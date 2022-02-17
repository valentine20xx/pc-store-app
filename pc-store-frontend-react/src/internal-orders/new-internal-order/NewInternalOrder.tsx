import {Button, Dialog, Snackbar, DialogActions, DialogContent, DialogTitle, Step, StepButton, Stepper, Zoom} from "@mui/material";
import React from "react";
import ClientDataStep from "./new-internal-order-steps/ClientDataStep";
import {TransitionProps} from "@mui/material/transitions";
import PersonalComputerSpecificationsStep from "./new-internal-order-steps/PersonalComputerSpecificationsStep";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
const NewInternalOrder = ({open, handleClose}) => {
    const [activeStep, setActiveStep] = React.useState(0);
    const [snackOpen, setSnackOpen] = React.useState(false);
    const [tab1Validation, setTab1Validation] = React.useState<Tab1Validation>({processor: {valid: true}, graphicsCard: {valid: true}});
    const [tab2Validation, setTab2Validation] = React.useState<Tab2Validation>({salutation: {valid: true}, name: {valid: true}, surname: {valid: true}});
    const [newInternalOrder, setNewInternalOrder] = React.useState<NewInternalOrderDTO>(
        {
            personalComputer: {processor: "", graphicsCard: ""},
            clientData: {name: "", surname: "", salutation: Saltuations.unset},
            files: [], privacyPolicy: false
        });

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
        } else {
            setSnackOpen(true);
        }
    }

    const resetHandle = () => {
        handleClose();
        setNewInternalOrder({
            personalComputer: {processor: "", graphicsCard: ""},
            clientData: {name: "", surname: "", salutation: Saltuations.male},
            files: [], privacyPolicy: false
        });
        setTab1Validation({processor: {valid: true}, graphicsCard: {valid: true}});
    }

    const isFromStepValid = (step: number): boolean => {
        let valid = false;

        if (step >= 0) {
            console.log("check step 0");
            const processorValue = newInternalOrder.personalComputer.processor.trim();
            const graphicsCardValue = newInternalOrder.personalComputer.graphicsCard.trim();

            if (processorValue != "") {
                tab1Validation.processor.valid = true;
                valid = true;
            } else {
                tab1Validation.processor.valid = false;
            }

            if (graphicsCardValue != "") {
                tab1Validation.graphicsCard.valid = true;
                valid = true;
            } else {
                tab1Validation.graphicsCard.valid = false;
            }
        }
        if (step >= 1) {
            console.log("check step 1");

            const salutationValue = newInternalOrder.clientData.salutation;
            if (salutationValue == Saltuations.male || salutationValue == Saltuations.female) {
                tab2Validation.salutation.valid = true;
                valid = true;
            } else {
                tab2Validation.salutation.valid = false;
                valid = false;
            }

        }
        if (step >= 2) {
            console.log("check step 2");
        }
        if (step >= 3) {
            console.log("check step 3");
        }

        setTab1Validation({...tab1Validation});
        console.log("return valid:", valid);
        return valid;
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
                                    setActiveStep(1)
                                } else {
                                    setSnackOpen(true);
                                }
                            }}>
                                <StepButton color="inherit">
                                    Client data
                                </StepButton>
                            </Step>
                            <Step onClick={event => {
                                console.log("go1:");
                                if (isFromStepValid(1)) {
                                    console.log("go:");
                                    setActiveStep(2)
                                } else {
                                    setSnackOpen(true);
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
                            resetHandle();
                        }
                        }>Create</Button>) :
                        (<Button onClick={nextClick}>Next</Button>)}
                </DialogActions>
            </Dialog>

            <Snackbar
                anchorOrigin={{vertical: "top", horizontal: "right"}}
                open={snackOpen}
                onClose={(event, reason) => {
                    setSnackOpen(false);
                }}
                autoHideDuration={5000}
                message="Errors!"
                key={"errorSnack"}
            />
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
    male = "male", female = "female", unset = " "
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
