import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import App from './App';
import InternalOrdersOverview from "./internal-orders/InternalOrdersOverview";

ReactDOM.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App/>}>
                <Route
                    path="/internal-orders-overview"
                    element={<InternalOrdersOverview/>}
                />
                <Route
                    path="/"
                    element={
                        <div style={{display: 'flex', flexDirection: 'row', flex: '1 1 100%', placeContent: 'center', alignItems: 'center'}}>
                            <div>
                                <h1>Herzlich willkommen !</h1>
                            </div>
                        </div>
                    }
                />
            </Route>
        </Routes>
    </BrowserRouter>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
