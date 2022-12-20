import {NewInternalOrderDTO} from "../../../../model/Model";
import React from "react";
import {convertSalutation} from "../../../../Utils";


export const SummaryStep = (props: { newInternalOrder: NewInternalOrderDTO }) => {
    return (<div>
        <table>
            <thead>
            <tr>
                <th align='left'>Name</th>
                <th align='left'>Value</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td align='left' colSpan={2}>Personal computer specifications</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption"><img src="/icons/computer-case-small.png" width="24" height="24"/>Computer case</td>
                <td align='center'>{props.newInternalOrder.personalComputer.computerCase}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption"><img src="/icons/motherboard-small.png" width="24" height="24"/>Motherboard</td>
                <td align='center'>{props.newInternalOrder.personalComputer.motherboard}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption"><img src="/icons/cpu-small.png" width="24" height="24"/>Processor</td>
                <td align='center'>{props.newInternalOrder.personalComputer.processor}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption"><img src="/icons/video-card-small.png" width="24" height="24"/>Graphics card</td>
                <td align='center'>{props.newInternalOrder.personalComputer.graphicsCard}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption"><img src="/icons/ram-memory-small.png" width="24" height="24"/>Random access memory</td>
                <td align='center'>{props.newInternalOrder.personalComputer.randomAccessMemory}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption"><img src="/icons/harddisk-small.png" width="24" height="24"/>Storage device</td>
                <td align='center'>{props.newInternalOrder.personalComputer.storageDevice}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption"><img src="/icons/power-small.png" width="24" height="24"/>Power supply unit</td>
                <td align='center'>{props.newInternalOrder.personalComputer.powerSupplyUnit}</td>
            </tr>
            <tr>
                <td align='left' colSpan={2}>Client data</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption">Salutation</td>
                <td align='center'>{convertSalutation(props.newInternalOrder.clientData.salutation)}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption">Name</td>
                <td align='center'>{props.newInternalOrder.clientData.name}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption">Surname</td>
                <td align='center'>{props.newInternalOrder.clientData.surname}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption">Street</td>
                <td align='center'>{props.newInternalOrder.clientData.street}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption">House number</td>
                <td align='center'>{props.newInternalOrder.clientData.houseNumber}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption">Zip</td>
                <td align='center'>{props.newInternalOrder.clientData.zip}</td>
            </tr>
            <tr>
                <td align='left' className="summary-table-row-caption">City</td>
                <td align='center'>{props.newInternalOrder.clientData.city}</td>
            </tr>
            {props.newInternalOrder.clientData.telephone && <tr>
                <td align='left' className="summary-table-row-caption">Telephone</td>
                <td align='center'>{props.newInternalOrder.clientData.telephone}</td>
            </tr>}
            {props.newInternalOrder.clientData.cellphone && <tr>
                <td align='left' className="summary-table-row-caption">Cellphone</td>
                <td align='center'>{props.newInternalOrder.clientData.cellphone}</td>
            </tr>}
            {props.newInternalOrder.clientData.email && <tr>
                <td align='left' className="summary-table-row-caption">e-Mail</td>
                <td align='center'>{props.newInternalOrder.clientData.email}</td>
            </tr>}
            </tbody>
        </table>
    </div>)
}

// <div>
//     <div className="final-section-title">Client data:</div>
//     <div className="final-section-sub-item">Salutation: {{clientDataForm.controls.salutation.value | referenceConverter:salutations}}</div>
//     <div className="final-section-sub-item">Name: {{clientDataForm.controls.name.value}}</div>
//     <div className="final-section-sub-item">Surname: {{clientDataForm.controls.surname.value}}</div>
//     <div className="final-section-sub-item">Street: {{clientDataForm.controls.street.value}}</div>
//     <div className="final-section-sub-item">House number: {{clientDataForm.controls.houseNumber.value}}</div>
//     <div className="final-section-sub-item">Zip: {{clientDataForm.controls.zip.value}}</div>
//     <div className="final-section-sub-item">City: {{clientDataForm.controls.city.value}}</div>
//     <div className="final-section-sub-item">Telephone: {{clientDataForm.controls.telephone.value}}</div>
//     <div className="final-section-sub-item">Cellphone: {{clientDataForm.controls.cellphone.value}}</div>
//     <div className="final-section-sub-item">eMail: {{clientDataForm.controls.email.value}}</div>
// </div>

