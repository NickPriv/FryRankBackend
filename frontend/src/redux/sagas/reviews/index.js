import { put, takeEvery } from 'redux-saga/effects'
import axios from 'axios';

import { types, reviewsActions } from '../../reducers/reviews';
import { ERROR_MESSAGE } from '../../constants';

const API_PATH = `/api/reviews`;

export function* callGetAllReviewsForRestaurant({ restaurantId }) {
    try {
        const { data } = yield axios.get(API_PATH, { params: { restaurantId } });
        yield put(reviewsActions.successfulGetAllReviewsForRestaurantRequest(data));
    } catch (err) {
        yield put(reviewsActions.failedGetAllReviewsForRestaurantRequest(ERROR_MESSAGE));
    }
}

export function* callCreateReviewForRestaurant({ review }) {
    try {
        yield axios.post(API_PATH, review);
        yield put(reviewsActions.successfulCreateReviewForRestaurantRequest());
    } catch (err) {
        yield put(reviewsActions.failedCreateReviewForRestaurantRequest(err.message));
    }
}

export default function* watchReviewsRequest() {
    yield takeEvery(types.GET_RESTAURANT_REVIEWS_REQUEST, callGetAllReviewsForRestaurant);
    yield takeEvery(types.CREATE_REVIEW_FOR_RESTAURANT_REQUEST, callCreateReviewForRestaurant);
}