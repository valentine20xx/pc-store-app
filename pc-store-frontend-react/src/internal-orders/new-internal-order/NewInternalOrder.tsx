import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from '@mui/material';
import React from 'react';

export interface NewInternalOrderInput {
    open: boolean;
    handleClose: any;
}

const NewInternalOrder: React.FC<NewInternalOrderInput> = ({
                                               open = false, handleClose = () => {
    }
                                           }) => {
    return (<Dialog open={open} onClose={handleClose}>
        <DialogTitle style={{display: 'flex', flexDirection: 'row'}}>
            <div style={{flex: "1 1 100%"}}>Subscribe</div>
            <Button onClick={handleClose}>X</Button>
        </DialogTitle>
        <DialogContent>
            <DialogContentText>
                To subscribe to this website, please enter your email address here. We
                will send updates occasionally.
            </DialogContentText>
        </DialogContent>
        <DialogActions>
            <Button onClick={handleClose}>Create</Button>
        </DialogActions>
    </Dialog>)
}

export default NewInternalOrder;
