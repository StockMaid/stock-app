import { React, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Stock } from "../components/Stock";

export const SkuPage = () => {
    const [stock, setStock] = useState([]);
    const { sku } = useParams();

    useEffect(() => {
        const fetchStocks = async () => {
            const response = await fetch(`/sku/${sku}`);
            const data = await response.json();
            setStock(data);
        };
        fetchStocks();
        const interval = setInterval(() => fetchStocks(), 5000);
        return () => clearInterval(interval);
    }, [sku]);

    return (
        <div>
            <Stock stock={stock} />
        </div>
    );
};
