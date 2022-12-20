import React from "react";
import {FormControl, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import {NewInternalOrderDTO, Saltuations} from "../../../../model/Model";

export const ClientDataStep = (props: { newInternalOrder: NewInternalOrderDTO, setNewInternalOrder: any, tab2Validation: any, setTab2Validation: any }) => {
    return (<div style={{display: "flex", flexDirection: "row", columnGap: "1em"}}>
        <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
            <FormControl fullWidth required error={!props.tab2Validation.salutation.valid}>
                <InputLabel id="salutation-label">Salutation</InputLabel>
                <Select key="salutation"
                        value={props.newInternalOrder.clientData.salutation ?? ""}
                        labelId="salutation-label"
                        label="Salutation"
                        onChange={event => {
                            if (event.target.value != null) {
                                props.newInternalOrder.clientData.salutation = (event.target.value as Saltuations);
                                props.setNewInternalOrder({...props.newInternalOrder});
                            }
                        }}>
                    <MenuItem value="male">Male</MenuItem>
                    <MenuItem value="female">Female</MenuItem>
                </Select>
            </FormControl>
            <TextField key="name" label="Name" variant="outlined" required
                       defaultValue={props.newInternalOrder.clientData.name}
                       error={!props.tab2Validation.name.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.name = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
            <TextField key="surname" label="Surname" variant="outlined" required
                       defaultValue={props.newInternalOrder.clientData.surname}
                       error={!props.tab2Validation.surname.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.surname = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
            <TextField key="street" label="Street" variant="outlined" required
                       defaultValue={props.newInternalOrder.clientData.street}
                       error={!props.tab2Validation.street.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.street = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
            <TextField key="houseNumber" label="House number" variant="outlined" required
                       defaultValue={props.newInternalOrder.clientData.houseNumber}
                       error={!props.tab2Validation.houseNumber.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.houseNumber = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
        </div>
        <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
            <TextField key="zip" label="Postleitzahl" variant="outlined" required
                       defaultValue={props.newInternalOrder.clientData.zip}
                       error={!props.tab2Validation.zip.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.zip = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
            <TextField key="city" label="Ort" variant="outlined" required
                       defaultValue={props.newInternalOrder.clientData.city}
                       error={!props.tab2Validation.city.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.city = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
            <TextField key="telephone" label="Telefon" variant="outlined"
                       defaultValue={props.newInternalOrder.clientData.telephone}
                       error={!props.tab2Validation.telephone.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.telephone = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
            <TextField key="cellphone" label="Handy" variant="outlined"
                       defaultValue={props.newInternalOrder.clientData.cellphone}
                       error={!props.tab2Validation.cellphone.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.cellphone = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
            <TextField key="email" label="E-mail" variant="outlined"
                       defaultValue={props.newInternalOrder.clientData.email}
                       error={!props.tab2Validation.email.valid}
                       onChange={event => {
                           props.newInternalOrder.clientData.email = event.target.value;
                           props.setNewInternalOrder({...props.newInternalOrder});
                       }}/>
        </div>
    </div>);
}
