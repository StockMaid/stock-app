import {
    KeyboardArrowDownRounded,
    KeyboardArrowUpRounded,
} from "@material-ui/icons";
import React from "react";
import styles from "./StockTable.module.css";
import { useState } from "react";
import { Link } from "react-router-dom";

const orderBy = (stock, value, direction) => {
    if (direction === "asc") {
        return [...stock].sort((a, b) => (a[value] > b[value] ? 1 : -1));
    }
    if (direction === "desc") {
        return [...stock].sort((a, b) => (a[value] > b[value] ? -1 : 1));
    }
    return stock;
};

const SortArrow = ({ direction }) => {
    if (!direction) {
        return <></>;
    }

    if (direction === "desc") {
        return (
            <div className={styles.heading_arrow}>
                <KeyboardArrowDownRounded color="inherit" />
            </div>
        );
    } else {
        return (
            <div className={styles.heading_arrow}>
                <KeyboardArrowUpRounded color="inherit" />
            </div>
        );
    }
};

export const StockTable = ({ stock }) => {
    const [direction, setDirection] = useState();
    const [value, setValue] = useState();

    const orderedStock = orderBy(stock, value, direction);

    const switchDirection = () => {
        if (!direction) {
            setDirection("desc");
        } else if (direction === "desc") {
            setDirection("asc");
        } else {
            setDirection(null);
        }
    };

    const setValueAndDirection = (value) => {
        switchDirection();
        setValue(value);
    };

    if (!stock) return null;

    return (
        <div>
            <div className={styles.heading}>
                <button
                    className={styles.heading_name}
                    onClick={() => setValueAndDirection("name")}
                >
                    <div>Name</div>
                    {value === "name" && <SortArrow direction={direction} />}
                </button>

                <button
                    className={styles.heading_population}
                    onClick={() => setValueAndDirection("regularPrice")}
                >
                    <div>Price</div>
                    {value === "regularPrice" && <SortArrow direction={direction} />}
                </button>

                {/* TODO: filter based on boolean values */}

                <button className={styles.heading_area}>
                    <div>In store</div>
                </button>

                <button className={styles.heading_gini}>
                    <div>Online</div>
                </button>
            </div>
            {orderedStock.map((stock) => (
                <Link to={`/skus/${stock.sku}`} key={stock.sku}>
                    <div className={`${styles.row} ${stock.inStoreAvailability || stock.onlineAvailability
                        ? styles.row_in_stock : styles.row_out_of_stock}`}>
                        <div className={styles.flag}>
                            <img src={stock.thumbnailImage} alt="alt" />
                        </div>
                        <div className={styles.name}>{stock.name}</div>
                        <div className={styles.population}>${stock.regularPrice}</div>
                        <div className={styles.area}>
                            {stock.inStoreAvailability ? (
                                <p className={styles.in_stock}>Available</p>
                            ) : (
                                <p className={styles.out_of_stock}>Unavailable</p>
                            )}
                        </div>
                        <div className={styles.gini}>
                            {stock.onlineAvailability ? (
                                <p className={styles.in_stock}>Available</p>
                            ) : (
                                <p className={styles.out_of_stock}>Unavailable</p>
                            )}
                        </div>
                    </div>
                </Link>
            ))}
        </div>
    );
};
