import { React, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "../Home.module.css";
import { StockTable } from "../components/StockTable";

export const ManufacturerPage = () => {
    const [stock, setStock] = useState([]);
    const { stockManufacturer } = useParams();
    const [keyword, setKeyword] = useState("");

    const filteredStock = stock.filter((stock) =>
        stock.name.toLowerCase().includes(keyword)
    );

    const onInputChange = (e) => {
        e.preventDefault();
        setKeyword(e.target.value.toLowerCase());
    };

    useEffect(() => {
        const fetchStocks = async () => {
            const response = await fetch(`/manufacturer/${stockManufacturer}`);
            const data = await response.json();
            setStock(data);
        };
        fetchStocks();
        const interval = setInterval(() => fetchStocks(), 5000);
        return () => clearInterval(interval);
    }, [stockManufacturer]);

    return (
        <div className={styles.container}>
            <div className={styles.inputContainer}>
                <div className={styles.counts}>Fetched {stock.length} items</div>
                <div className={styles.input}>

                </div>
            </div>
            <StockTable stock={filteredStock} />
        </div>
    );
};
