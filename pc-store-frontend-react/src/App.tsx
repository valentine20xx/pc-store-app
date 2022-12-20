import React from "react";
import "./App.css";
import {AppBar, Drawer, IconButton, List, ListItem, ListItemButton, ListItemText, Toolbar, Typography, useTheme} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import {Outlet, useNavigate} from "react-router-dom";

export const App = () => {
    const [state, setState] = React.useState({
        menu: false
    });
    const theme = useTheme();

    const navigate = useNavigate();

    const openMenu = (): void => {
        setState({...state, menu: true});
    }

    const closeMenu = (): void => {
        setState({...state, menu: false});
    }

    const internalOrdersClick = (): void => {
        setState({...state, menu: false});
        navigate("/internal-orders-overview");
    }

    return (
        <div style={{display: "flex", flexDirection: "column", flexGrow: "1"}}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        onClick={openMenu}>
                        <MenuIcon/>
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1, textAlign: "center"}}>
                        PC Store
                    </Typography>
                </Toolbar>
            </AppBar>
            <div style={{display: "flex", flex: "1 1 100%"}}>
                <Outlet/>
            </div>
            <NavigationMenu state={state} closeMenu={closeMenu} internalOrdersClick={internalOrdersClick}/>
            <div style={{
                backgroundColor: theme.palette.primary.main,
                padding: "0.5em", height: "3em",
                display: "flex", flexDirection: "row", justifyContent: "end", alignItems: "center"
            }}>
                <div style={{color: theme.palette.common.white}}>
                    Footer
                </div>
            </div>
        </div>
    );
}

const NavigationMenu = (props: { state: { menu: boolean }, closeMenu: () => void, internalOrdersClick: () => void }) => {
    return (
        <Drawer anchor={"left"}
                open={props.state.menu}
                onClose={props.closeMenu}>
            <div style={{width: "15em"}}>
                <h2 style={{textAlign: "center"}}>Menu</h2>
                <List>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemText primary="Internal orders"
                                          onClick={props.internalOrdersClick}
                            />
                        </ListItemButton>
                    </ListItem>
                </List>
            </div>
        </Drawer>
    );
}
