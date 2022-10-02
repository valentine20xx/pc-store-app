import {Button, Dialog, DialogActions, DialogContent, DialogTitle, Step, StepButton, Stepper, Zoom} from "@mui/material";
import React from "react";
import ClientDataStep from "./new-internal-order-steps/ClientDataStep";
import {TransitionProps} from "@mui/material/transitions";
import PersonalComputerSpecificationsStep from "./new-internal-order-steps/PersonalComputerSpecificationsStep";
import {useSnackbar} from "notistack";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
const NewInternalOrder = ({open, handleClose}) => {
    const [activeStep, setActiveStep] = React.useState(0);
    const [tab1Validation, setTab1Validation] = React.useState<Tab1Validation>({processor: {valid: true}, graphicsCard: {valid: true}});
    const [tab2Validation, setTab2Validation] = React.useState<Tab2Validation>({salutation: {valid: true}, name: {valid: true}, surname: {valid: true}});
    const [newInternalOrder, setNewInternalOrder] = React.useState<NewInternalOrderDTO>(
        {
            personalComputer: {processor: "", graphicsCard: ""},
            clientData: {name: "", surname: "", salutation: Saltuations.unset},
            files: [], privacyPolicy: false
        });

    const {enqueueSnackbar, closeSnackbar} = useSnackbar();

    const activeTab = (tab: number) => {
        switch (tab) {
            case 0:
                return PersonalComputerSpecificationsStep({newInternalOrder, setNewInternalOrder, tab1Validation, setTab1Validation});
            case 1:
                return ClientDataStep({newInternalOrder, setNewInternalOrder, tab2Validation, setTab2Validation});
            case            2            :
                return AdditionalFilesStep();
            case            3            :
                return (<div>Summary</div>);
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

    const resetHandle = () => {
        handleClose();
        setNewInternalOrder({
            personalComputer: {processor: "", graphicsCard: ""},
            clientData: {name: "", surname: "", salutation: Saltuations.unset},
            files: [], privacyPolicy: false
        });
        setTab1Validation({processor: {valid: true}, graphicsCard: {valid: true}});
    }

    const isFromStepValid = (step: number): boolean => {
        let valid = true;

        if (step >= 0) {
            const processorValue = newInternalOrder.personalComputer.processor.trim();
            const graphicsCardValue = newInternalOrder.personalComputer.graphicsCard.trim();

            if (processorValue != "") {
                tab1Validation.processor.valid = true;
            } else {
                tab1Validation.processor.valid = valid = false;
            }

            if (graphicsCardValue != "") {
                tab1Validation.graphicsCard.valid = true;
            } else {
                tab1Validation.graphicsCard.valid = valid = false;
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
        const fd = new FormData();

        const defaultObject = {
            clientData: {
                cellphone: "+49528252826",
                city: "Nürnberg",
                email: "example@test.de",
                houseNumber: 110,
                street: "Hauptstraße",
                telephone: "+49528252826",
                zip: 90459
            },
            personalComputer: {
                computerCase: "MSI MAG Forge 100R",
                motherboard: "Gigabyte B550 Aorus Pro V2",
                powerSupplyUnit: "700W - be quiet! Pure power 11",
                randomAccessMemory: "32GB Corsair Vengeance LPX DDR4-3000",
                storageDevice: "250GB Samsung 870 EVO"
            },
            privacyPolicy: true
        }

        fd.set("internal-order", JSON.stringify({
            clientData: {...newInternalOrder.clientData, ...defaultObject.clientData},
            personalComputer: {...newInternalOrder.personalComputer, ...defaultObject.personalComputer},
            privacyPolicy: newInternalOrder.privacyPolicy
        }));

        const requestOptions: RequestInit = {
            method: "POST",
            // headers: {"Content-Type": "multipart/form-data"},
            body: fd
        };
        fetch("http://localhost:8080/internal-order-multipart", requestOptions)
            .then(async response => {
                console.log(response);
                // const isJson = response.headers.get('content-type')?.includes('application/json');
                // const data = isJson && await response.json();
                //
                // // check for error response
                // if (!response.ok) {
                //     // get error message from body or default to response status
                //     const error = (data && data.message) || response.status;
                //     return Promise.reject(error);
                // }
                // element.innerHTML = data.id;
            })
            .catch(error => {
                console.error(error);
                // element.parentElement.innerHTML = `Error: ${error}`;
                // console.error('There was an error!', error);
            });
    }

    return (
        <React.Fragment>
            <Dialog open={open} onClose={resetHandle} TransitionComponent={ZoomTransition}
                    TransitionProps={{
                        onExited: () => {
                            setActiveStep(0);
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
                            // eslint-disable-next-line no-console
                            createNewElement();
                            resetHandle();
                        }
                        }>Create</Button>) :
                        (<Button onClick={nextClick}>Next</Button>)}
                </DialogActions>
            </Dialog>
        </React.Fragment>
    )
}

export default NewInternalOrder;

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

export enum Saltuations {
    male = "male", female = "female", unset = "unset"
}

export interface NewInternalOrderDTO {
    personalComputer: {
        processor: string;
        graphicsCard: string;
    };
    clientData: {
        salutation: Saltuations;
        name: string;
        surname: string;
    };
    privacyPolicy: boolean;
    files: Array<{
        id: string;
        name: string;
        note: string;
    }>;
}

export interface Tab1Validation {
    processor: {
        valid: boolean;
        message?: string;
    };
    graphicsCard: {
        valid: boolean;
        message?: string;
    };
}

export interface Tab2Validation {
    salutation: {
        valid: boolean;
        message?: string;
    };
    name: {
        valid: boolean;
        message?: string;
    };
    surname: {
        valid: boolean;
        message?: string;
    };
}

// const [internalOrderDTO, setInternalOrderDTO] = React.useState<NewInternalOrderDTO>({
//     clientData: {name: "", salutation: "male", surname: ""},
//     files: [],
//     personalComputer: {graphicsCard: "", processor: ""},
//     privacyPolicy: false
// });
