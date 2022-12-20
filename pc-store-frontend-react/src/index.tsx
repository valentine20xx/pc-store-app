import React from "react";
import ReactDOM from 'react-dom/client';
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {App} from "./App";
import {InternalOrdersOverview} from "./modules/InternalOrdersOverview";
import {SnackbarProvider} from "notistack";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={
                <SnackbarProvider maxSnack={7} autoHideDuration={null} anchorOrigin={{vertical: "top", horizontal: "right"}} preventDuplicate={true}>
                    <App/>
                </SnackbarProvider>
            }>
                <Route
                    path="/internal-orders-overview"
                    element={<InternalOrdersOverview/>}
                />
                <Route
                    path="/"
                    element={
                        <div style={{display: "flex", flexDirection: "row", flex: "1 1 100%", placeContent: "center", alignItems: "center"}}>
                            <div>
                                <h1>Herzlich willkommen !</h1>
                            </div>
                        </div>
                    }
                />
            </Route>
        </Routes>
    </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
