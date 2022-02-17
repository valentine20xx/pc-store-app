import React from "react";
import {Button, Fab, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import NewInternalOrder from "./new-internal-order/NewInternalOrder";
import "./InternalOrdersOverview.css";
// import {Edit as EditIcon} from '@mui/icons-material';
import EditIcon from "@mui/icons-material/Edit";


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
    const rows: InternalOrderShortDTO[] = [
        {
            id: generateId(),
            version: new Date(),
            client: "Ololo, Trololo",
            personalComputer: "i7, 6700XT",
            status: "open",
            dateOfReceiving: new Date(),
        }
    ];

    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
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
                        {rows.map((row) => (
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
