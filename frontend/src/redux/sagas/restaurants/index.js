import { call, delay, put, takeEvery } from 'redux-saga/effects'
import axios from 'axios';

import { types, restaurantsActions } from '../../reducers/restaurants';

export function* callGetRestaurants() {
    try {
        const API_PATH = 'http://localhost:3000/restaurants';
        const { data } = yield axios.get(API_PATH);
        yield put(restaurantsActions.successfulGetRestaurantsRequest(data));
    } catch (err) {
        yield put(restaurantsActions.failedGetRestaurantsRequest('Failed getting restaurants'));
    }
}

export default function* watchRestaurantsRequest() {
    yield takeEvery(types.GET_RESTAURANTS_REQUEST, callGetRestaurants);
}