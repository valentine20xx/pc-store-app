import React, {useEffect, useState} from "react";
import {Button, Fab, Paper, Table, TableBody, TableCell, TableContainer, TablePagination, TableHead, TableRow} from "@mui/material";
import NewInternalOrder from "./new-internal-order/NewInternalOrder";
import "./InternalOrdersOverview.css";
// import {Edit as EditIcon} from '@mui/icons-material';
import EditIcon from "@mui/icons-material/Edit";
import moment from "moment";

export interface DefaultDTOObject {
    id: string;
    version: Date;
}

export interface InternalOrderShortDTO extends DefaultDTOObject {
    client: string;
    personalComputer: string;
    status: string;
    dateOfReceiving: Date;
}

const InternalOrdersOverview = () => {
    // const rows: InternalOrderShortDTO[] = [
    //     {
    //         id: generateId(),
    //         version: new Date(),
    //         client: "Ololo, Trololo",
    //         personalComputer: "i7, 6700XT",
    //         status: "open",
    //         dateOfReceiving: new Date(),
    //     }
    // ];

    const [rows, setRows] = useState<InternalOrderShortDTO[]>([]);

    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        fetch("http://localhost:8080/internal-order-list")
            .then(value => {
                return value.json();
            })
            .then(
                (result) => {
                    const objs = (result as InternalOrderShortDTO[]).map(value => {
                        return {...value, dateOfReceiving: moment(value.dateOfReceiving, "yyyy-MM-dd").toDate()}
                    })
                    setRows(objs);
                },
                (error) => {
                    console.error(error);
                }
            )
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
        <div className="internal-orders-overview">
            <div style={{display: "flex", flexDirection: "row", gap: "1em"}}>
                <Button variant="contained" onClick={handleClickOpen}>Create a new order</Button>
                <Button variant="contained" color={"info"}>Refresh</Button>
            </div>

            <NewInternalOrder open={open} handleClose={handleClose}/>

            <TableContainer component={Paper}>
                <Table size={"small"}>
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
                        {rows
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
                            ))}
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

        </div>
    );
}

export function generateId() {
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, (c: string) => {
        const r = Math.random() * 16 | 0;
        // eslint-disable-next-line no-mixed-operators
        const v = c === "x" ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

export default InternalOrdersOverview;
