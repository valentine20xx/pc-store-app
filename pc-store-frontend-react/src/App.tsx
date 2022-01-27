import React from 'react';
import './App.css';
import {AppBar, Drawer, IconButton, List, Toolbar, Typography, ListItem, ListItemButton, ListItemText} from "@mui/material";
import Menu from "@mui/icons-material/Menu";
import {Outlet, useNavigate} from "react-router-dom";

const App = () => {
    const [state, setState] = React.useState({
        menu: false
    });

    const navigate = useNavigate();

    const openMenu = (event: any): void => {
        setState({...state, menu: true});
    }

    const closeMenu = (event: any, reason: "backdropClick" | "escapeKeyDown"): void => {
        setState({...state, menu: false});
    }

    const internalOrdersClick = (): void => {
        setState({...state, menu: false});
        navigate("/internal-orders-overview");
    }

    return (
        <div style={{display: "flex", flexDirection: "column", flexGrow: '1'}}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        sx={{mr: 2}}
                        onClick={openMenu}
                    >
                        <Menu/>
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1, textAlign: 'center'}}>
                        PC Store
                    </Typography>
                </Toolbar>
            </AppBar>
            <div style={{display: 'flex', flex: '1 1 100%'}}>
                <Outlet/>
            </div>
            <NavigationMenu state={state} closeMenu={closeMenu} internalOrdersClick={internalOrdersClick}/>
        </div>
    );
}
// TODO: fix ignore
// @ts-ignore
const NavigationMenu = ({state, closeMenu, internalOrdersClick}) => {

    return (
        <Drawer anchor={'left'}
                open={state.menu}
                onClose={closeMenu}>
            <div style={{width: "15em"}}>
                <h2 style={{textAlign: "center"}}>Menu</h2>
                <List>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemText primary="Internal orders"
                                          onClick={internalOrdersClick}
                            />
                        </ListItemButton>
                    </ListItem>
                </List>
            </div>
        </Drawer>
    );
}

export default App;
