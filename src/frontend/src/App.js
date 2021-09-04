import "./App.css";
import { HashRouter as Router, Route, Switch } from "react-router-dom";
import { ManufacturerPage } from "./pages/ManufacturerPage";
import { DatePage } from "./pages/DatePage";
import { SkuPage } from "./pages/SkuPage";
import { HomePage } from "./pages/HomePage";
import Navbar from "./navbar/Navbar";
import ScrollToTop from "./components/ScrollToTop";

function App() {
    return (
        <div className="App">
            <Router>
                <ScrollToTop />
                <Navbar />
                <Switch>
                    <Route path="/skus/:sku">
                        <SkuPage />
                    </Route>
                    <Route path="/manufacturers/:stockManufacturer">
                        <ManufacturerPage />
                    </Route>
                    <Route path="/dates/:from/:to">
                        <DatePage />
                    </Route>
                    <Route path="/">
                        <HomePage />
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;