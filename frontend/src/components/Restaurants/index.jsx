import { PropTypes } from 'prop-types';
import { Fragment } from 'react';

const propTypes = {
    restaurants: PropTypes.array.isRequired,
    error: PropTypes.string.isRequired
}

const Restaurants = ({ restaurants, error }) => {

    const addressDisplay = (address) => {
        return (
            <Fragment>
                <p>Street Address: {address.streetNumberAndName}, City: {address.city}, State: {address.stateAbbr}, Zip: {address.zipCode}</p>
            </Fragment>
        );
    }

    const restaurantsDisplay = (restaurants) => {
        return restaurants.map((restaurant, i) => (
            <Fragment key = {i}>
                <p><b>{restaurant.name}</b> </p>
                <p>{addressDisplay(restaurant.address)}</p>
            </Fragment>
        ));
    }

    if(!restaurants) {
        return <p>{error}</p>;
    }
    else if (restaurants.length === 0) {
        return <p>No restaurants exist yet</p>
    }
    else {
        return (
            <div>
                {restaurantsDisplay(restaurants)}
            </div>
        );
    }
}

Restaurants.propTypes = propTypes;

export default Restaurants;