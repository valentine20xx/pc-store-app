import React from "react";
import ReactDOM from 'react-dom/client';
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./App";
import {InternalOrdersOverview} from "./modules/InternalOrdersOverview";
import {SnackbarProvider} from "notistack";
import {Provider} from 'react-redux'
import {store} from "./state/store";
import {ThemeProvider, createTheme} from '@mui/material/styles';
import {Typography} from "@mui/material";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

const theme = createTheme({
    palette: {
        mode: 'light',
        // mode: 'dark',
    },
});

root.render(
    <Provider store={store}>
        <ThemeProvider theme={theme}>
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
                                <div style={{backgroundColor: theme.palette.background.default, display: "flex", flexDirection: "row", flex: "1 1 100%", placeContent: "center", alignItems: "center"}}>
                                    <div>
                                        {/*<h1>Herzlich willkommen !</h1>*/}
                                        <Typography style={{color: theme.palette.text.primary}} variant="h1" fontWeight="bold">Herzlich willkommen !</Typography>
                                    </div>
                                </div>
                            }
                        />
                    </Route>
                </Routes>
            </BrowserRouter>
        </ThemeProvider>
    </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
