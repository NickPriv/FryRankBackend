import { all, fork } from 'redux-saga/effects';
import restaurantsSaga from './restaurants';
import reviewsSaga from './reviews';

export default function* rootSaga() {
    yield all([
        fork(reviewsSaga),
        fork(restaurantsSaga),
    ]);
}