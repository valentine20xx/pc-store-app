import React from 'react';
import './App.css';
import {AppBar, Drawer, IconButton, List, Toolbar, Typography} from '@mui/material';

import Menu from '@mui/icons-material/Menu';

import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';

export default function App() {
    const [state, setState] = React.useState({
        menu: false
    });

    function openMenu(event: any) {
        console.log('event', event);
        setState({...state, menu: true});
    }

    function closeMenu(event: any, reason: "backdropClick" | "escapeKeyDown"): void {
        console.log('event', event, ', reason', reason);
        setState({...state, menu: false});
    }

    return (
        <React.Fragment>
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
            <React.Fragment key='left'>
                <Drawer
                    anchor={'left'}
                    open={state.menu}
                    onClose={closeMenu}
                >
                    <div style={{width: "15em"}}>
                        <h2 style={{textAlign: "center"}}>Menu</h2>
                        <List>
                            <ListItem disablePadding>
                                <ListItemButton>
                                    <ListItemText primary="Internal orders"/>
                                </ListItemButton>
                            </ListItem>
                        </List>
                    </div>
                </Drawer>
            </React.Fragment>
        </React.Fragment>
    );
}
