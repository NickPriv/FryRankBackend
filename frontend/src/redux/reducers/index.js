import { combineReducers } from 'redux';
import restaurantsReducer from './restaurants';
import reviewsReducer from './reviews';

export default combineReducers({
    reviewsReducer,
    restaurantsReducer,
});