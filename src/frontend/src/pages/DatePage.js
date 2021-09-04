import { React, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "../Home.module.css";
import { StockTable } from "../components/StockTable";
import SearchInput from "../components/SearchInput";

export const DatePage = () => {
    const [stocks, setStocks] = useState([]);
    const { from, to } = useParams();
    const [keyword, setKeyword] = useState("");

    const filteredStock = stocks.filter((stock) =>
        stock.name.toLowerCase().includes(keyword)
    );

    const onInputChange = (e) => {
        e.preventDefault();
        setKeyword(e.target.value.toLowerCase());
    };

    useEffect(() => {
        const fetchStocks = async () => {
            const response = await fetch(`/date/${from}/${to}`);
            const data = await response.json();
            setStocks(data);
        };
        fetchStocks();
        const interval = setInterval(() => fetchStocks(), 5000);
        return () => clearInterval(interval);
    }, [from, to]);

    return (
        <div className={styles.container}>
            <div className={styles.inputContainer}>
                <div className={styles.counts}>Fetched {stocks.length} items</div>
                <div className={styles.input}>
                    <SearchInput placeholder="Filter by Name" onChange={onInputChange} />
                </div>
            </div>
            <StockTable stock={filteredStock} />
        </div>
    );
};
