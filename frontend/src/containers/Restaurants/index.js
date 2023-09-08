import { compose, lifecycle } from 'react-recompose';
import { connect } from 'react-redux';
import Restaurants from '../../components/Restaurants';
import { restaurantsActions } from '../../redux/reducers/restaurants'

const mapStateToProps = (state) => {
    return {
        restaurants: state.restaurantsReducer.restaurants,
        error: state.restaurantsReducer.error
    }
}

const mapDispatchToProps = {
    getRestaurants: restaurantsActions.startGetRestaurantsRequest
};

export default compose(
    connect(mapStateToProps, mapDispatchToProps),
    lifecycle({
        componentDidMount() {
            const { getRestaurants } = this.props;
            getRestaurants();
        },
    }),
)(Restaurants);