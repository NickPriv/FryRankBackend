import { call, put, takeEvery } from 'redux-saga/effects'
import axios from 'axios';

import { types, reviewsActions } from '../../reducers/reviews';
import { ERROR_MESSAGE } from '../../constants';

export function* callGetAllReviewsForRestaurant({ restaurantId }) {
    try {
        const API_PATH = 'http://localhost:3000/reviews';
        const { data } = yield axios.get(API_PATH, { params: { restaurantId } });
        yield put(reviewsActions.successfulGetAllReviewsForRestaurantRequest(data));
    } catch (err) {
        yield put(reviewsActions.failedGetAllReviewsForRestaurantRequest(ERROR_MESSAGE));
    }
}

export default function* watchReviewsRequest() {
    yield takeEvery(types.GET_RESTAURANT_REVIEWS_REQUEST, callGetAllReviewsForRestaurant);
}