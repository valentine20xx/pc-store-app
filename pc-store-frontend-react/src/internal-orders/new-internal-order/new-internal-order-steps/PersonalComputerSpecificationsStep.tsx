import {TextField} from "@mui/material";
import React from "react";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
const PersonalComputerSpecificationsStep = ({newInternalOrder, setNewInternalOrder, tab1Validation, setTab1Validation}) => {
    return (
        <div style={{display: "flex", flexDirection: "row", columnGap: "1em"}}>
            <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
                <TextField label="Computer case" variant="outlined"/>
                <TextField label="Motherboard" variant="outlined"/>
                <TextField key="processor" label="Processor" variant="outlined" required
                           defaultValue={newInternalOrder.personalComputer.processor}
                           error={!tab1Validation.processor.valid}
                           onChange={event => {
                               newInternalOrder.personalComputer.processor = event.target.value;
                               setNewInternalOrder({...newInternalOrder});
                           }}
                />
                <TextField key="graphicsCard" label="Graphics card" variant="outlined" required
                           defaultValue={newInternalOrder.personalComputer.graphicsCard}
                           error={!tab1Validation.graphicsCard.valid}
                           onChange={event => {
                               newInternalOrder.personalComputer.graphicsCard = event.target.value;
                               setNewInternalOrder({...newInternalOrder});
                           }}
                />
            </div>
            <div style={{display: "flex", flexDirection: "column", rowGap: "1em", flexGrow: 1}}>
                <TextField label="Random access memory" variant="outlined"/>
                <TextField label="Storage device" variant="outlined"/>
                <TextField label="Power supply unit" variant="outlined"/>
            </div>
        </div>
    );
}
export default PersonalComputerSpecificationsStep;
