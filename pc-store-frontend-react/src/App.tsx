import React from "react";
import "./App.css";
import {AppBar, Avatar, Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Drawer, IconButton, List, ListItem, ListItemButton, ListItemText, Menu, MenuItem, TextField, Toolbar, Typography, useTheme} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import {Outlet, useNavigate} from "react-router-dom";
import PersonIcon from '@mui/icons-material/Person';
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "./state/store";
import {clearAuthentication, ROLES, setAuthentication} from "./state/userSlice";
import {ajax} from "rxjs/internal/ajax/ajax";

const App = () => {
    const [menuState, setMenuState] = React.useState({
        menu: false
    });
    const theme = useTheme();

    const navigate = useNavigate();

    const internalOrdersClick = (): void => {
        setMenuState({...menuState, menu: false});
        navigate("/internal-orders-overview");
    }

    const user = useSelector((state: RootState) => state.user)
    const dispatch = useDispatch()

    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: React.MouseEvent<HTMLElement>) => {
        console.log(event.currentTarget);
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const [loginOpen, setLoginOpen] = React.useState(false);

    const openLoginDialog = () => {
        setLoginOpen(true);
    };

    const loginDialogClearClicked = () => {
        // setLoginOpen(false);
        setCredentials({username: "", password: ""})
        setLoginError({hasError: false})
    };

    const [credentials, setCredentials] = React.useState<{
        username: string;
        password: string;
    }>({username: "", password: ""});

    const [loginError, setLoginError] = React.useState<{
        hasError: boolean;
        message?: string
    }>({hasError: false});

    const loginDialogLoginClicked = () => {
        ajax.post<{
            token: string;
        }>("/get-token", {
            "username": credentials.username,
            "password": credentials.password
        }, {
            "Content-Type": "application/json"
        }).subscribe(value => {
            const token = value.response.token;
            const splitedToken = token.split(".");

            const payloadAsString: string = atob(splitedToken[1]);
            const payloadAsJSON: { sub: string, roles: Array<ROLES>, fullname: string } = JSON.parse(payloadAsString);

            dispatch(setAuthentication({isAuthenticated: true, fullname: payloadAsJSON.fullname, token: value.response.token, roles: payloadAsJSON.roles}));
            setCredentials({username: "", password: ""})
            setLoginOpen(false);
        }, error => {
            console.error(error);

            switch (error.status) {
                case 500:
                    setLoginError({hasError: true, message: "Connection error"})
                    break;
                case 400:
                    setLoginError({hasError: true, message: "Wrong credentials"})
                    break;
            }

            // setLoginError({hasError: true, message: "Wrong credentials"})
        })
    };

    return (
        <div style={{display: "flex", flexDirection: "column", flexGrow: "1"}}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        onClick={() => {
                            setMenuState({...menuState, menu: true});
                        }}>
                        <MenuIcon/>
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1, textAlign: "center"}}>
                        PC Store
                    </Typography>
                    {user.isAuthenticated ? <div style={{display: "flex", flexDirection: "row", alignItems: "center", columnGap: "1em"}}>
                            <p>
                                {user.fullname}
                            </p>
                            <Avatar className="link" onClick={event => {
                                handleClick(event)
                            }}>
                                <PersonIcon/>
                            </Avatar>
                            <Menu
                                anchorEl={anchorEl}
                                open={open}
                                onClose={handleClose}
                                anchorOrigin={{
                                    vertical: 'bottom',
                                    horizontal: 'center',
                                }}
                                transformOrigin={{
                                    vertical: 'top',
                                    horizontal: 'center',
                                }}>
                                <MenuItem onClick={() => {
                                    dispatch(clearAuthentication());
                                    handleClose();
                                }}>Logout</MenuItem>
                            </Menu>
                        </div> :
                        <Button variant="text" color="inherit" onClick={() => {
                            openLoginDialog()
                            // dispatch(setAuthentication({isAuthenticated: true, fullname: "fullname", token: "value.response.token"}));
                        }}>Login</Button>
                    }
                    <Dialog open={loginOpen} onClose={() => {
                        setLoginOpen(false);
                        setCredentials({username: "", password: ""})
                        setLoginError({hasError: false})
                    }}>
                        <DialogTitle>Log In</DialogTitle>
                        <DialogContent>
                            <TextField
                                autoFocus
                                error={loginError.hasError}
                                helperText={loginError.message}
                                margin="normal"
                                id="name"
                                label="Username"
                                type="text"
                                fullWidth
                                variant="outlined"
                                value={credentials.username}
                                onChange={event => {
                                    setCredentials({
                                        ...credentials,
                                        username: event.target.value
                                    })
                                }}
                                onKeyDown={event => {
                                    if (event.key == "Enter") {
                                        loginDialogLoginClicked();
                                    }
                                }}/>
                            <TextField
                                error={loginError.hasError}
                                helperText={loginError.message}
                                margin="normal"
                                id="name"
                                label="Password"
                                type="password"
                                fullWidth
                                variant="outlined"
                                value={credentials.password}
                                onChange={event => {
                                    setCredentials({
                                        ...credentials,
                                        password: event.target.value
                                    })
                                }}
                                onKeyDown={event => {
                                    if (event.key == "Enter") {
                                        loginDialogLoginClicked();
                                    }
                                }}/>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={loginDialogClearClicked}>Clear</Button>
                            <Button onClick={loginDialogLoginClicked}>Login</Button>
                        </DialogActions>
                    </Dialog>
                </Toolbar>
            </AppBar>
            <div style={{display: "flex", flex: "1 1 100%"}}>
                <Outlet/>
            </div>
            <NavigationMenu state={menuState} closeMenu={(): void => {
                setMenuState({...menuState, menu: false});
            }} internalOrdersClick={internalOrdersClick}/>
            <Box style={{
                backgroundColor: theme.palette.mode == "dark" ? theme.palette.grey["900"] : theme.palette.primary.main,
                padding: "0.5em", height: "3em",
                display: "flex", flexDirection: "row", justifyContent: "end", alignItems: "center"
            }}>
                {/*<div style={{color: theme.palette.common.white}}>*/}
                {/*    Footer*/}
                {/*</div>*/}
            </Box>
        </div>
    );
}

interface INavigationMenu {
    state: { menu: boolean },
    closeMenu: () => void,
    internalOrdersClick: () => void
}

const NavigationMenu = (props: INavigationMenu) => {
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
                                          onClick={props.internalOrdersClick}/>
                        </ListItemButton>
                    </ListItem>
                </List>
            </div>
        </Drawer>
    );
}

export default App;
