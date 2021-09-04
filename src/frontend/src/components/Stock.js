import styles from "./Stock.module.css";

export const Stock = ({ stock }) => {
    if (!stock) return null;
    const dateNow = new Date().toISOString().split("T")[0];
    const inStoreDiffInMs =
        new Date(dateNow) - new Date(stock.inStoreAvailabilityUpdateDate);
    const onlineDiffInMs =
        new Date(dateNow) - new Date(stock.onlineAvailabilityUpdateDate);
    const inStoreDiffInDays = inStoreDiffInMs / (1000 * 60 * 60 * 24);
    const onlineDiffInDays = onlineDiffInMs / (1000 * 60 * 60 * 24);

    let releaseDate = stock.releaseDate;
    if (stock.releaseDate === "2030-01-02") releaseDate = "Unknown";
    let skuText = "View Product";
    let skuLink = stock.url;
    let inStock = stock.inStoreAvailability || stock.onlineAvailability;
    if (inStock) skuLink = stock.addToCartUrl;
    if (inStock) skuText = "Add to cart";

    return (
        <div className={styles.container}>
            <div className={styles.container_left}>
                <div className={styles.overview_panel}>
                    <img src={stock.image} alt={stock.alternateViewsImage}/>

                    <h1 className={styles.overview_name}>{stock.name}</h1>
                    <div className={styles.overview_region}>{stock.sku}</div>

                    <div className={styles.overview_numbers}>
                        <div className={styles.overview_population}>
                            <div className={styles.overview_value}>
                                {stock.inStoreAvailability ? (
                                    <p className={styles.in_stock}>Available</p>
                                ) : (
                                    <p className={styles.out_of_stock}>Unavailable</p>
                                )}
                            </div>
                            <div className={styles.overview_label}>
                                In Store Availability
                            </div>
                        </div>

                        <div className={styles.overview_area}>
                            <div className={styles.overview_value}>
                                {stock.onlineAvailability ? (
                                    <p className={styles.in_stock}>Available</p>
                                ) : (
                                    <p className={styles.out_of_stock}>Unavailable</p>
                                )}
                            </div>
                            <div className={styles.overview_label}>Online Availability</div>
                        </div>
                    </div>
                </div>
            </div>
            <div className={styles.container_right}>
                <div className={styles.details_panel}>
                    <h4 className={styles.details_panel_heading}>Details</h4>

                    <div className={styles.details_panel_row}>
                        <div className={styles.details_panel_label}>Price</div>
                        <div className={styles.details_panel_value}>
                            ${stock.regularPrice}
                        </div>
                    </div>

                    <div className={styles.details_panel_row}>
                        <div className={styles.details_panel_label}>Release date</div>
                        <div className={styles.details_panel_value}>{releaseDate}</div>
                    </div>

                    <div className={styles.details_panel_row}>
                        <div className={styles.details_panel_label}>
                            Updated in store
                        </div>
                        <div className={styles.details_panel_value}>
                            {inStoreDiffInDays} days ago
                        </div>
                    </div>

                    <div className={styles.details_panel_row}>
                        <div className={styles.details_panel_label}>
                            Updated online
                        </div>
                        <div className={styles.details_panel_value}>
                            {onlineDiffInDays} days ago
                        </div>
                    </div>

                    <div className={styles.details_panel_row}>
                        <div className={styles.details_panel_label}>Link</div>
                        <div className={styles.details_panel_value}>
                            {inStock ? (
                                <a target="_blank" rel="noreferrer" href={skuLink}>
                                    {skuText}
                                </a>
                            ) : (
                                <a target="_blank" rel="noreferrer" href={skuLink}>
                                    {skuText}
                                </a>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
