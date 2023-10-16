import * as React from 'react';
import { IFormStyleItem, IRecordAttribute, IRecordEntity, IRecordValue } from '../../pev-interface/IRecord';
import { Box } from '@mui/material';
import { TRecordElementClass } from '../../pev-type/TRecord';

type StyledElementProps = {
    type: string;
    entity?: IRecordEntity;
    attribute?: IRecordAttribute;
    value?: IRecordValue;
};

const StyledElement = (props: StyledElementProps) => {
    let element: IRecordEntity | IRecordAttribute | IRecordValue | null = null;

    if (props.type === 'entity') element = props.entity || null;
    if (props.type === 'attribute') element = props.attribute || null;
    if (props.type === 'value') element = props.value || null;

    const LabelElement = () => {
        return <label>{element?.text}</label>;
    };

    const TextBoxElement = () => {
        return <input type={`text`} value={element?.text || ''} readOnly={true} />;
    };

    const RichTextBoxElement = () => {
        return <textarea value={element?.text || ''} readOnly={true} />;
    };

    const CheckBoxElement = () => {
        return (
            <label style={{ display: 'flex', alignItems: 'center' }}>
                <input type={`checkbox`} checked={element?.text === '1'} readOnly={true} />
                <span>{element?.textDesc}</span>
            </label>
        );
    };

    const RadioButtonElement = (): JSX.Element => {
        return (
            <label style={{ display: 'flex', alignItems: 'flex-end' }}>
                <input type={`radio`} checked={element?.text === '1'} readOnly={true} />
                <span>{element?.textDesc}</span>
            </label>
        );
    };

    const ImageElement = (): JSX.Element => {
        return <img src={element?.text ? `https://hisimg.snuh.org/${element?.text}` : ''} alt={element?.text} />;
    };

    const handleClick = () => {
        console.log('element', element?.formStyleItem);
    };

    const getColor = (color: string) => {
        return `#${color.substring(3)}`;
    };

    const getBoxShadow = (formStyleItem: IFormStyleItem): string => {
        if (!formStyleItem.borderThickness) return `none`;

        const borderArr = formStyleItem.borderThickness.split(',');

        if (borderArr.length === 4) {
            let top = borderArr[1];
            let left = borderArr[0];
            let right = borderArr[2];
            let bottom = borderArr[3];

            return `0 ${top}px ${getColor(formStyleItem.borderBrush)} inset, ${right}px 0 ${getColor(
                formStyleItem.borderBrush
            )}, ${left}px 0 ${getColor(formStyleItem.borderBrush)} inset, 0 ${bottom}px ${getColor(formStyleItem.borderBrush)}`;
        }

        return 'none';
    };

    const getJustifyContent = (formStyleItem: IFormStyleItem): string => {
        if (formStyleItem.hContentAlignment === 'Left') return 'flex-start';
        if (formStyleItem.hContentAlignment === 'Right') return 'flex-end';
        return 'center';
    };

    if (element === null || element.formStyleItem === null) return null;
    if (element.formStyleItem.id === '-1000') return null;
    if (element.formStyleItem.visibility === 'Collapsed') return null;

    return (
        <Box
            className={'record-element'}
            sx={{
                position: 'absolute',
                zIndex: isNaN(Number(element.formStyleItem.zIndex)) ? 0 : Number(element.formStyleItem.zIndex),
                top: `${element.formStyleItem.top}px`,
                left: `${element.formStyleItem.left}px`,
                width: `${element.formStyleItem.width}px`,
                height: `${
                    isNaN(Number(element.formStyleItem.height))
                        ? Number(element.formStyleItem.minHeight)
                        : Number(element.formStyleItem.height)
                }px`,
                textAlign: element.formStyleItem.textAlignment?.toLowerCase() || 'left',
                fontStyle: element.formStyleItem.fontStyle?.toLowerCase() || 'normal',
                fontWeight: element.formStyleItem.fontWeight?.toLowerCase() || 'normal',
                fontSize: `${Number(element.formStyleItem.fontSize) - 2}px`,
                backgroundColor: getColor(element.formStyleItem.background),
                color: getColor(element.formStyleItem.foreGround),
                boxShadow: getBoxShadow(element.formStyleItem),
                justifyContent: getJustifyContent(element.formStyleItem),
                alignItems: element.formStyleItem.vContentAlignment?.toLowerCase() || 'center',
                paddingLeft: isNaN(Number(element.formStyleItem.indentUnit)) ? 0 : `${element.formStyleItem.indentUnit}px`
            }}
            onClick={handleClick}
        >
            {element.formStyleItem.type === TRecordElementClass.LABEL && LabelElement()}
            {element.formStyleItem.type === TRecordElementClass.TEXT_BOX && TextBoxElement()}
            {element.formStyleItem.type === TRecordElementClass.RICH_TEXT_BOX && RichTextBoxElement()}
            {element.formStyleItem.type === TRecordElementClass.CHECK_BOX && CheckBoxElement()}
            {element.formStyleItem.type === TRecordElementClass.RADIO_BUTTON && RadioButtonElement()}
            {element.formStyleItem.type === TRecordElementClass.IMAGE && ImageElement()}
        </Box>
    );
};

export default StyledElement;
