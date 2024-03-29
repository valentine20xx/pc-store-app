import React from "react";
import {Box, Button, Fab, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, Typography, useTheme} from "@mui/material";
import "./InternalOrdersOverview.css";
import EditIcon from "@mui/icons-material/Edit";
import moment from "moment";
import {NewInternalOrder} from "./internal-orders/new-internal-order/NewInternalOrder";
import {InternalOrderShortDTO} from "../model/Model";
import {useSelector} from "react-redux";
import {RootState} from "../state/store";
import {ajax} from "rxjs/internal/ajax/ajax";
import {ROLES} from "../state/userSlice";

export const InternalOrdersOverview = () => {
    const [rows, setRows] = React.useState<InternalOrderShortDTO[]>([]);
    const [open, setOpen] = React.useState(false);

    const user = useSelector((state: RootState) => state.user);
    const theme = useTheme();
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = (success: boolean) => {
        setOpen(false);
        if (success) {
            loadOrderList();
        }
    };

    const loadOrderList = (): void => {
        if (user.isAuthenticated) {
            ajax.get<Array<InternalOrderShortDTO>>("/internal-order-list", {
                "Authorization": `Bearer ${user.token}`
            }).subscribe(value => {
                console.log(value)
                const objs = value.response.map(value => {
                    return {...value, dateOfReceiving: moment(value.dateOfReceiving, "yyyy-MM-dd").toDate()}
                })
                setRows(objs);
            }, error => {
                console.error(error);
            })
        }
    }

    React.useEffect(() => {
        loadOrderList();
    }, [])

    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    return (
        <Box className="internal-orders-overview">

            <Box style={{display: "flex", flexDirection: "row", gap: "1em"}}>
                <Button variant="contained" onClick={handleClickOpen} disabled={!user.isAuthenticated || !user.roles.includes(ROLES.EDIT)}>Create a new order</Button>
                <Button variant="contained" color="info" onClick={() => {
                    loadOrderList();
                }} disabled={!user.isAuthenticated || !user.roles.includes(ROLES.READ)}>Refresh</Button>
            </Box>

            <NewInternalOrder open={open} handleClose={handleClose}/>

            <TableContainer component={Paper}>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell>Client</TableCell>
                            <TableCell align="left">Personal computer</TableCell>
                            <TableCell align="left">Status</TableCell>
                            <TableCell align="left">Date of receiving</TableCell>
                            <TableCell align="left">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {user.isAuthenticated ?
                            user.roles.includes(ROLES.READ) ?
                                rows
                                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                    .map((row) => (
                                        <TableRow key={row.id}>
                                            <TableCell align="left" style={{width: "35%"}}>{row.client}</TableCell>
                                            <TableCell align="left" style={{width: "35%"}}>{row.personalComputer}</TableCell>
                                            <TableCell align="left" style={{width: "15%"}}>{row.status}</TableCell>
                                            <TableCell align="left" style={{width: "15%"}}>{row.dateOfReceiving.toLocaleDateString()}</TableCell>
                                            <TableCell align="right" style={{width: "0"}}>
                                                <Fab color="primary" size="small">
                                                    <EditIcon/>
                                                </Fab>
                                            </TableCell>
                                        </TableRow>
                                    ))
                                :
                                <TableRow>
                                    <TableCell align="center" colSpan={5}>
                                        <Typography variant="h5">The user does not have a permission</Typography>
                                    </TableCell>
                                </TableRow>
                            : <TableRow>
                                <TableCell align="center" colSpan={5}>
                                    <Typography variant="h5">No authentication</Typography>
                                </TableCell>
                            </TableRow>}
                    </TableBody>
                </Table>
            </TableContainer>

            <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={rows.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Box>
    );
}
