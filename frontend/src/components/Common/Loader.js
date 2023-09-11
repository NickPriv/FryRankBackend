import React from 'react'
import {TailSpin} from "react-loader-spinner";

export default function LoaderComponent() {
    return <TailSpin color="rgb(211,211,211)" height={70} width={70} timeout={5000}/>
}
