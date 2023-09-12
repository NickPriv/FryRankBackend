import React from 'react'
import { Spinner } from 'reactstrap'

const FrySpinner = () => {
    return (
        <Spinner color="warning">
            <p className="visually-hidden">Loading...</p>
        </Spinner>
    )
}

export default FrySpinner;
