import React from 'react';
import {Button, IconButton, Paper, SvgIcon, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from '@mui/material';
import {faPen} from '@fortawesome/free-solid-svg-icons';
import NewInternalOrder from './new-internal-order/NewInternalOrder';
import './InternalOrdersOverview.css';

type FontAwesomeSvgIconProps = {
    icon: any;
};

const FontAwesomeSvgIcon = React.forwardRef<SVGSVGElement, FontAwesomeSvgIconProps>(
    (props, ref) => {
        const {icon} = props;

        const {
            icon: [width, height, , , svgPathData],
        } = icon;

        return (
            <SvgIcon ref={ref} viewBox={`0 0 ${width} ${height}`}>
                {typeof svgPathData === 'string' ? (
                    <path d={svgPathData}/>
                ) : (
                    /**
                     * A multi-path Font Awesome icon seems to imply a duotune icon. The 0th path seems to
                     * be the faded element (referred to as the "secondary" path in the Font Awesome docs)
                     * of a duotone icon. 40% is the default opacity.
                     *
                     * @see https://fontawesome.com/how-to-use/on-the-web/styling/duotone-icons#changing-opacity
                     */
                    svgPathData.map((d: string, i: number) => (
                        <path style={{opacity: i === 0 ? 0.4 : 1}} d={d}/>
                    ))
                )}
            </SvgIcon>
        );
    },
);

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
            client: 'Ololo, Trololo',
            personalComputer: 'i7, 6700XT',
            status: 'open',
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
            <div style={{display: 'flex', flexDirection: 'row', gap: '1em'}}>
                <Button variant="contained" onClick={handleClickOpen}>Create a new order</Button>
                <Button variant="contained" color={'info'}>Refresh</Button>
            </div>

            <NewInternalOrder open={open} handleClose={handleClose}/>

            <TableContainer component={Paper}>
                <Table size={'small'}>
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
                                <TableCell align="left" style={{width: '25%'}}>{row.client}</TableCell>
                                <TableCell align="left" style={{width: '25%'}}>{row.personalComputer}</TableCell>
                                <TableCell align="left" style={{width: '25%'}}>{row.status}</TableCell>
                                <TableCell align="left" style={{width: '25%'}}>{row.dateOfReceiving.toLocaleDateString()}</TableCell>
                                <TableCell align="right" style={{width: '0'}}>
                                    <IconButton>
                                        <FontAwesomeSvgIcon icon={faPen}/>
                                    </IconButton>
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
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c: string) => {
        const r = Math.random() * 16 | 0;
        // eslint-disable-next-line no-mixed-operators
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

export default InternalOrdersOverview;
