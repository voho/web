import React from "react";

interface IVariousComponentTypesProps {
    name: string;
    city: string;
}

export const FunctionalComponentAsConstant: React.FC<IVariousComponentTypesProps> = (props) => {
    return <span>Hello, {props.name}! How is living in {props.city}?</span>;
};

export function FunctionalComponentAsFunction(props: IVariousComponentTypesProps) {
    return <span>Hello, {props.name}! How is living in {props.city}?</span>;
}

export class ClassComponent extends React.Component<IVariousComponentTypesProps> {
    public render() {
        return <span>Hello, {this.props.name}! How is living in {this.props.city}?</span>;
    }
}
