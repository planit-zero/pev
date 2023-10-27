import * as React from 'react';
import { IRecordItem } from '../../pev-interface/IRecordDataR';
import { TRecordElementClass } from '../../pev-type/TRecord';

type RecordElementProps = {
    item: IRecordItem;
};

const LabelElement = (item: IRecordItem): JSX.Element => {
    return <label>{item.text}</label>;
};

const TextBoxElement = (item: IRecordItem): JSX.Element => {
    return <input type={`text`} value={item.value || ''} style={{ width: '100%', height: '100%' }} readOnly={true} />;
};

const RichTextBoxElement = (item: IRecordItem): JSX.Element => {
    return <textarea value={item.value || ''} style={{ width: '100%', height: '100%' }} readOnly={true} />;
};

const CheckBoxElement = (item: IRecordItem): JSX.Element => {
    return (
        <label>
            <input type={`checkbox`} checked={item.value === '1'} readOnly={true} />
            <span>{item.text}</span>
        </label>
    );
};

const RadioButtonElement = (item: IRecordItem): JSX.Element => {
    return (
        <label>
            <input type={`radio`} checked={item.value === '1'} readOnly={true} />
            <span>{item.text}</span>
        </label>
    );
};

const ImageElement = (item: IRecordItem): JSX.Element => {
    return <img src={item.value ? `${item.value}` : ''} alt={item.text} />;
};

const getBoxShadow = (item: IRecordItem): string => {
    if (!item.borderThickness) return `none`;

    const borderArr = item.borderThickness.split(',');

    if (borderArr.length === 4) {
        let top = borderArr[1];
        let left = borderArr[0];
        let right = borderArr[2];
        let bottom = borderArr[3];

        return `
    0 ${top}px #808080 inset,
    ${right}px 0 #808080,
    ${left}px 0 #808080 inset, 
    0 ${bottom}px #808080
    `;
    }

    return 'none';
};

const getJustifyContent = (item: IRecordItem): string => {
    if (item.hContentAlignment === 'Left') return 'flex-start';
    if (item.hContentAlignment === 'Right') return 'flex-end';
    return 'center';
};

const getBackgroundColor = (item: IRecordItem): string => {
    if (item.background) {
        const color = item.background.substring(3, 9);
        if (color !== 'FFFFFF') return `#${color}`;
    }

    return 'transparent';
};

const RecordElement = (props: RecordElementProps) => {
    // if (props.item.id === '-1000') return null;
    // if (props.item.visibility !== 'Visible') return null;

    // @ts-ignore
    // @ts-ignore
    return (
        <div
            className={`record-element`}
            onClick={() => console.log(`###`, props.item)}
            style={{
                position: 'absolute',
                zIndex: isNaN(Number(props.item.zIndex)) ? 0 : Number(props.item.zIndex),
                top: isNaN(Number(props.item.top)) ? 0 : Number(props.item.top),
                left: isNaN(Number(props.item.left)) ? 0 : Number(props.item.left),
                width: isNaN(Number(props.item.width)) ? 0 : Number(props.item.width),
                height: isNaN(Number(props.item.height)) ? Number(props.item.minHeight) : Number(props.item.height),
                minWidth: isNaN(Number(props.item.minWidth)) ? 0 : Number(props.item.minWidth),
                minHeight: isNaN(Number(props.item.minHeight)) ? 0 : Number(props.item.minHeight),
                color: props.item.foreGround ? `#${props.item.foreGround.substring(3, 9)}` : '#000000',
                fontSize: isNaN(Number(props.item.fontSize)) ? 0 : Number(props.item.fontSize) - 2,
                fontStyle: props.item.fontStyle,
                fontWeight: props.item.fontWeight,
                // @ts-ignore
                textAlign: props.item.textAlignment,
                backgroundColor: getBackgroundColor(props.item),
                boxShadow: getBoxShadow(props.item),
                paddingLeft: isNaN(Number(props.item.indentUnit)) ? 0 : Number(props.item.indentUnit),
                justifyContent: getJustifyContent(props.item),
                alignItems: props.item.vContentAlignment ? props.item.vContentAlignment.toLowerCase() : 'center'
            }}
        >
            {props.item.type === TRecordElementClass.LABEL && LabelElement(props.item)}
            {props.item.type === TRecordElementClass.TEXT_BOX && TextBoxElement(props.item)}
            {props.item.type === TRecordElementClass.RICH_TEXT_BOX && RichTextBoxElement(props.item)}
            {props.item.type === TRecordElementClass.CHECK_BOX && CheckBoxElement(props.item)}
            {props.item.type === TRecordElementClass.RADIO_BUTTON && RadioButtonElement(props.item)}
            {props.item.type === TRecordElementClass.IMAGE && ImageElement(props.item)}
        </div>
    );
};

export default RecordElement;
