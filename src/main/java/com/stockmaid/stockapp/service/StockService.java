package com.stockmaid.stockapp.service;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stockmaid.stockapp.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private Environment environment;

    private static final String BESTBUY_API_KEY1 = "BESTBUY_API_KEY1";
    private static final String BESTBUY_API_KEY2 = "BESTBUY_API_KEY2";
    private static final String WEBHOOK_TOKEN = "WEBHOOK_TOKEN";

    private List<Long> skus = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();

    public List<Stock> getAllStocks() {
        return stocks;
    }

    @Scheduled(fixedRate = 1000)
    private void getData() {
        try {
            getBestBuyData(environment.getRequiredProperty(BESTBUY_API_KEY1));
            checkAvailability();
        } catch (HttpClientErrorException e) {
            getBestBuyData(environment.getRequiredProperty(BESTBUY_API_KEY2));
            checkAvailability();
        }
    }

    private void getBestBuyData(String key) {
        String URL = "https://api.bestbuy.com/v1/products(sku in(6407309, 6468926, 6429442, 6429434, 6465789, 6462956, 6454329, 6467808, 6471615, 6432400, 6434198, 6439299, 6444444, 6472637, 6468863, 6471285, 6467500, 6465803, 6467497, 6471286, 6471287, 6462173, 6438279, 6452940, 6438278, 6441020, 6440913, 6444716, 6441172, 6472646, 6462266, 6447182, 6457632, 6430175, 6456926, 6430215, 6432446, 6432447, 6466931, 6467840, 6466932, 6467838, 6439127, 6460665, 6460666, 6439128, 6432445, 6452573, 6475237, 6475224, 6475226, 6475238, 6475223, 6475228, 6457993, 6468934, 6445108, 6453897, 6467788, 6471958, 6471957, 6467782, 6468932, 6467785, 6437909, 6468931, 6467779, 6466561, 6468925, 6468910, 6468928, 6439384, 6437912, 6442485, 6442484, 6453894, 6471960, 6457994, 6454689, 6453895, 6460664, 6453267, 6457619, 6474679, 6474545, 6453268, 6449499, 6474557, 6457624, 6441226, 6442077, 6444358, 6442585, 6444357, 6457622, 6457626, 6457620, 6445157, 6467289, 6454318))?pageSize=100&cursorMark=*&format=csv&apiKey=";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.execute(URL + key, HttpMethod.GET, null, clientHttpResponse -> {
            InputStreamReader reader = new InputStreamReader(clientHttpResponse.getBody());
            CsvToBean<Stock> csvToBean = new CsvToBeanBuilder<Stock>(reader)
                    .withType(Stock.class)
                    .withSkipLines(1)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return this.stocks = csvToBean.stream().collect(Collectors.toList());
        });
    }

    private void checkAvailability() {
        List<Stock> inStock = getAllStocks().stream()
                .filter(stock -> stock.isOnlineAvailability() || stock.isInStoreAvailability())
                .collect(Collectors.toList());
        for (Stock item: inStock) {
            if (!skus.contains(item.getSku())) {
                sendWebhook(
                        item.getName(),
                        item.getUrl(),
                        item.getThumbnailImage(),
                        Boolean.toString(item.isInStoreAvailability()),
                        Boolean.toString(item.isOnlineAvailability())
                );
                skus.add(item.getSku());
            }
        }
    }

    private void sendWebhook(String name, String url, String thumbnailImage, String inStore, String online) {
        // Create builder
        WebhookClientBuilder builder = new WebhookClientBuilder(
                "https://discord.com/api/webhooks/885354613765701642/" + environment.getRequiredProperty(WEBHOOK_TOKEN)
        );
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Hello");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        WebhookClient client = builder.build();
        // Send embed
        WebhookEmbed.EmbedTitle title = new WebhookEmbed.EmbedTitle(name, url);
        WebhookEmbed.EmbedField field1 = new WebhookEmbed.EmbedField(true,"In Store Availability", inStore);
        WebhookEmbed.EmbedField field2 = new WebhookEmbed.EmbedField(true,"Online Availability", online);
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setTitle(title)
                .setThumbnailUrl(thumbnailImage)
                .addField(field1)
                .addField(field2)
                .setColor(2817936)
                .build();
        client.send(embed);
    }

    public List<Stock> getAll() {
        return getAllStocks();
    }

    public Stock getBySku(long sku) throws Exception {
        return getAllStocks().stream()
                .filter(stock -> stock.getSku() == sku)
                .findFirst()
                .orElseThrow(() -> new Exception("sku not found"));
    }

    public List<Stock> getByManufacturer(String manufacturer) {
        return getAllStocks().stream()
                .filter(stock -> stock.getManufacturer().equals(manufacturer))
                .collect(Collectors.toList());
    }

    public List<Stock> getDate(LocalDate from, LocalDate to) {
        return getAllStocks().stream()
                .filter(stock -> isBetween(stock, from, to))
                .collect(Collectors.toList());
    }

    private boolean isBetween(Stock stock, LocalDate from, LocalDate to) {
        LocalDate date = stock.getReleaseDate();
        return date.isAfter(from) && date.isBefore(to);
    }
}
