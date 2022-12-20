import {Button, Dialog, DialogActions, DialogContent, DialogTitle, Step, StepButton, Stepper, Zoom} from "@mui/material";
import React from "react";
import {TransitionProps} from "@mui/material/transitions";
import {useSnackbar} from "notistack";
import {ClientDataStep} from "./new-internal-order-steps/ClientDataStep";
import {PersonalComputerSpecificationsStep} from "./new-internal-order-steps/PersonalComputerSpecificationsStep";
import {defaultNewInternalOrderDTO, NewInternalOrderDTO, Saltuations} from "../../../model/Model";
import {isNotEmpty} from "../../../Utils";
import {SummaryStep} from "./new-internal-order-steps/SummaryStep";


export const NewInternalOrder = (props: { open: any, handleClose: any }) => {
    const [activeStep, setActiveStep] = React.useState(0);
    const [tab1Validation, setTab1Validation] = React.useState<Tab1Validation>(defaultTab1Validation());
    const [tab2Validation, setTab2Validation] = React.useState<Tab2Validation>(defaultTab2Validation());
    const [newInternalOrder, setNewInternalOrder] = React.useState<NewInternalOrderDTO>(defaultNewInternalOrderDTO);

    const {enqueueSnackbar, closeSnackbar} = useSnackbar();

    const activeTab = (tab: number) => {
        switch (tab) {
            case 0:
                return PersonalComputerSpecificationsStep({newInternalOrder, setNewInternalOrder, tab1Validation, setTab1Validation});
            case 1:
                return ClientDataStep({newInternalOrder, setNewInternalOrder, tab2Validation, setTab2Validation});
            case 2:
                return AdditionalFilesStep();
            case 3:
                return SummaryStep({newInternalOrder});
            default:
                return (<div>Unknown tab</div>);
        }
    }

    const stepLength = (): number => {
        return 3;
    }

    const nextClick = () => {
        if (isFromStepValid(activeStep) && activeStep < stepLength()) {
            setActiveStep(activeStep + 1);
            closeSnackbar();
        } else {
            enqueueSnackbar("Validation error", {variant: "error"});
        }
    }


    const resetHandle = (success: boolean) => {
        props.handleClose(success);
        setNewInternalOrder(defaultNewInternalOrderDTO);
        setTab1Validation(defaultTab1Validation());
    }

    const isFromStepValid = (step: number): boolean => {
        let valid = true;

        if (step >= 0) {
            if (isNotEmpty(newInternalOrder.personalComputer.computerCase)) {
                tab1Validation.computerCase.valid = true;
            } else {
                tab1Validation.computerCase.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.personalComputer.motherboard)) {
                tab1Validation.motherboard.valid = true;
            } else {
                tab1Validation.motherboard.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.personalComputer.processor)) {
                tab1Validation.processor.valid = true;
            } else {
                tab1Validation.processor.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.personalComputer.graphicsCard)) {
                tab1Validation.graphicsCard.valid = true;
            } else {
                tab1Validation.graphicsCard.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.personalComputer.randomAccessMemory)) {
                tab1Validation.randomAccessMemory.valid = true;
            } else {
                tab1Validation.randomAccessMemory.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.personalComputer.storageDevice)) {
                tab1Validation.storageDevice.valid = true;
            } else {
                tab1Validation.storageDevice.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.personalComputer.powerSupplyUnit)) {
                tab1Validation.powerSupplyUnit.valid = true;
            } else {
                tab1Validation.powerSupplyUnit.valid = valid = false;
            }

            setTab1Validation({...tab1Validation});
        }
        if (step >= 1) {
            const salutationValue = newInternalOrder.clientData.salutation;
            if (salutationValue == Saltuations.male || salutationValue == Saltuations.female) {
                tab2Validation.salutation.valid = true;
            } else {
                tab2Validation.salutation.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.clientData.name)) {
                tab2Validation.name.valid = true;
            } else {
                tab2Validation.name.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.clientData.surname)) {
                tab2Validation.surname.valid = true;
            } else {
                tab2Validation.surname.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.clientData.street)) {
                tab2Validation.street.valid = true;
            } else {
                tab2Validation.street.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.clientData.houseNumber)) {
                tab2Validation.houseNumber.valid = true;
            } else {
                tab2Validation.houseNumber.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.clientData.zip)) {
                tab2Validation.zip.valid = true;
            } else {
                tab2Validation.zip.valid = valid = false;
            }

            if (isNotEmpty(newInternalOrder.clientData.city)) {
                tab2Validation.city.valid = true;
            } else {
                tab2Validation.city.valid = valid = false;
            }

            setTab2Validation({...tab2Validation});
        }
        if (step >= 2) {
            console.log("check step 2");
        }
        if (step >= 3) {
            console.log("check step 3");
        }

        console.log("return valid:", valid);
        return valid;
    }

    const createNewElement = () => {
        const formData = new FormData();

        const newInternalOrderAsString = JSON.stringify(newInternalOrder)
        console.debug(newInternalOrder, newInternalOrderAsString);

        formData.set("internal-order", newInternalOrderAsString);

        fetch("http://localhost:8080/internal-order-multipart", {
            method: "POST",
            // headers: {"Content-Type": "multipart/form-data"},
            body: formData
        }).then(async response => {
            console.log(response);
            const isJson = response.headers.get('content-type')?.includes('application/json');
            const data = isJson && await response.json();
            console.debug('isJson', isJson, 'data', data);
            // // check for error response
            // if (!response.ok) {
            //     // get error message from body or default to response status
            //     const error = (data && data.message) || response.status;
            //     return Promise.reject(error);
            // }
            // element.innerHTML = data.id;
        }).catch(error => {
            console.error(error);
            // element.parentElement.innerHTML = `Error: ${error}`;
            // console.error('There was an error!', error);
        });
    }

    return (
        <Dialog open={props.open} onClose={resetHandle} TransitionComponent={ZoomTransition}
                TransitionProps={{
                    onExited: () => {
                        setActiveStep(0);
                        closeSnackbar();
                    }
                }}>
            <DialogTitle>
                New order
            </DialogTitle>
            <DialogContent>
                <div style={{padding: "0.5em", display: "flex", flexDirection: "column", rowGap: "2em"}}>
                    <Stepper activeStep={activeStep}>
                        <Step onClick={event => {
                            setActiveStep(0)
                        }}>
                            <StepButton color="inherit">
                                Personal computer specifications
                            </StepButton>
                        </Step>
                        <Step onClick={event => {
                            if (isFromStepValid(0)) {
                                setActiveStep(1);
                                closeSnackbar();
                            } else {
                                enqueueSnackbar("Validation error", {variant: "error"});
                            }
                        }}>
                            <StepButton color="inherit">
                                Client data
                            </StepButton>
                        </Step>
                        <Step onClick={event => {
                            if (isFromStepValid(1)) {
                                setActiveStep(2);
                                closeSnackbar();
                            } else {
                                enqueueSnackbar("Validation error", {variant: "error"});
                            }
                        }}>
                            <StepButton color="inherit">
                                Additional files
                            </StepButton>
                        </Step>
                    </Stepper>
                    {activeTab(activeStep)}
                </div>
            </DialogContent>
            <DialogActions>
                {activeStep === stepLength() ?
                    (<Button onClick={() => {
                        createNewElement();
                        resetHandle(true);
                    }
                    }>Create</Button>) :
                    (<Button onClick={nextClick}>Next</Button>)}
            </DialogActions>
        </Dialog>
    )
}

const AdditionalFilesStep = () => {
    return (<div>Additional files</div>)
};

const ZoomTransition = React.forwardRef((
    props: TransitionProps & {
        children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>) => {
    return (<Zoom ref={ref} {...props} />);
});

// export enum Saltuations {
//     male = "male", female = "female", none = ""
// }

export interface Tab1Validation {
    computerCase: ValidationObject;
    motherboard: ValidationObject;
    processor: ValidationObject;
    graphicsCard: ValidationObject;
    randomAccessMemory: ValidationObject;
    storageDevice: ValidationObject;
    powerSupplyUnit: ValidationObject;
}

export interface Tab2Validation {
    salutation: ValidationObject;
    name: ValidationObject;
    surname: ValidationObject;
    street: ValidationObject;
    houseNumber: ValidationObject;
    zip: ValidationObject;
    city: ValidationObject;
    telephone?: ValidationObject;
    cellphone?: ValidationObject;
    email?: ValidationObject;

}

export const defaultTab1Validation = (): Tab1Validation => {
    return {
        computerCase: {valid: true},
        motherboard: {valid: true},
        processor: {valid: true},
        graphicsCard: {valid: true},
        randomAccessMemory: {valid: true},
        storageDevice: {valid: true},
        powerSupplyUnit: {valid: true}
    }
}


export const defaultTab2Validation = (): Tab2Validation => {
    return {
        salutation: {valid: true},
        name: {valid: true},
        surname: {valid: true},
        street: {valid: true},
        houseNumber: {valid: true},
        zip: {valid: true},
        city: {valid: true},
        telephone: {valid: true},
        cellphone: {valid: true},
        email: {valid: true}
    }
}

export interface ValidationObject {
    valid: boolean;
    message?: string;
}

// const [internalOrderDTO, setInternalOrderDTO] = React.useState<NewInternalOrderDTO>({
//     clientData: {name: "", salutation: "male", surname: ""},
//     files: [],
//     personalComputer: {graphicsCard: "", processor: ""},
//     privacyPolicy: false
// });
