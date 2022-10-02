import React from "react";
import {FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {Saltuations} from "../NewInternalOrder";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
const ClientDataStep = ({newInternalOrder, setNewInternalOrder, tab2Validation, setTab2Validation}) => {
    return (<div style={{display: "flex", flexDirection: "row", columnGap: "1em"}}>
        <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
            <FormControl fullWidth>
                <InputLabel id="salutation-label">Salutation</InputLabel>
                <Select key="salutation"
                        value={newInternalOrder.clientData.salutation ?? " "}
                        labelId="salutation-label"
                        label="Salutation"
                        onChange={event => {
                            if (event.target.value != Saltuations.unset) {
                                newInternalOrder.clientData.salutation = event.target.value;
                                setNewInternalOrder({...newInternalOrder});
                            }
                        }}
                >
                    <MenuItem value="unset">(<i>Please select ...</i>)</MenuItem>
                    <MenuItem value="male">Male</MenuItem>
                    <MenuItem value="female">Female</MenuItem>
                </Select>
            </FormControl>
            <TextField key="name" label="Name" variant="outlined"
                       defaultValue={newInternalOrder.clientData.name}
                       error={!tab2Validation.name.valid}
                       onChange={event => {
                           newInternalOrder.clientData.name = event.target.value;
                           setNewInternalOrder({...newInternalOrder});
                       }}
            />
            <TextField key="surname" label="Surname" variant="outlined"
                       defaultValue={newInternalOrder.clientData.surname}
                       error={!tab2Validation.surname.valid}
                       onChange={event => {
                           newInternalOrder.clientData.surname = event.target.value;
                           setNewInternalOrder({...newInternalOrder});
                       }}
            />
            <TextField defaultValue={""} label="Street" variant="outlined"/>
            <TextField defaultValue={""} label="House number" variant="outlined"/>
        </div>
        <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
            <TextField label="Postleitzahl" variant="outlined"/>
            <TextField label="Ort" variant="outlined"/>
            <TextField label="Telefon" variant="outlined"/>
            <TextField label="Handy" variant="outlined"/>
            <TextField label="E-mail" variant="outlined"/>
        </div>
    </div>);
}

export default ClientDataStep;
