import {TextField} from "@mui/material";
import React from "react";
import {NewInternalOrderDTO} from "../../../../model/Model";
import {Tab1Validation} from "../NewInternalOrder";

export const PersonalComputerSpecificationsStep = (props: { newInternalOrder: NewInternalOrderDTO, setNewInternalOrder: any, tab1Validation: Tab1Validation, setTab1Validation: any }) => {
    return (
        <div style={{display: "flex", flexDirection: "row", columnGap: "1em"}}>
            <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
                <TextField label="Computer case" variant="outlined" required
                           defaultValue={props.newInternalOrder.personalComputer.computerCase}
                           error={!props.tab1Validation.computerCase.valid}
                           onChange={event => {
                               props.newInternalOrder.personalComputer.computerCase = event.target.value;
                               props.setNewInternalOrder({...props.newInternalOrder});
                           }}/>
                <TextField label="Motherboard" variant="outlined" required
                           defaultValue={props.newInternalOrder.personalComputer.motherboard}
                           error={!props.tab1Validation.motherboard.valid}
                           onChange={event => {
                               props.newInternalOrder.personalComputer.motherboard = event.target.value;
                               props.setNewInternalOrder({...props.newInternalOrder});
                           }}/>
                <TextField key="processor" label="Processor" variant="outlined" required
                           defaultValue={props.newInternalOrder.personalComputer.processor}
                           error={!props.tab1Validation.processor.valid}
                           onChange={event => {
                               props.newInternalOrder.personalComputer.processor = event.target.value;
                               props.setNewInternalOrder({...props.newInternalOrder});
                           }}/>
                <TextField key="graphicsCard" label="Graphics card" variant="outlined" required
                           defaultValue={props.newInternalOrder.personalComputer.graphicsCard}
                           error={!props.tab1Validation.graphicsCard.valid}
                           onChange={event => {
                               props.newInternalOrder.personalComputer.graphicsCard = event.target.value;
                               props.setNewInternalOrder({...props.newInternalOrder});
                           }}/>
            </div>
            <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
                <TextField label="Random access memory" variant="outlined" required
                           defaultValue={props.newInternalOrder.personalComputer.randomAccessMemory}
                           error={!props.tab1Validation.randomAccessMemory.valid}
                           onChange={event => {
                               props.newInternalOrder.personalComputer.randomAccessMemory = event.target.value;
                               props.setNewInternalOrder({...props.newInternalOrder});
                           }}/>
                <TextField label="Storage device" variant="outlined" required
                           defaultValue={props.newInternalOrder.personalComputer.storageDevice}
                           error={!props.tab1Validation.storageDevice.valid}
                           onChange={event => {
                               props.newInternalOrder.personalComputer.storageDevice = event.target.value;
                               props.setNewInternalOrder({...props.newInternalOrder});
                           }}/>
                <TextField label="Power supply unit" variant="outlined" required
                           defaultValue={props.newInternalOrder.personalComputer.powerSupplyUnit}
                           error={!props.tab1Validation.powerSupplyUnit.valid}
                           onChange={event => {
                               props.newInternalOrder.personalComputer.powerSupplyUnit = event.target.value;
                               props.setNewInternalOrder({...props.newInternalOrder});
                           }}/>
            </div>
        </div>
    );
}
