export const types = {
    GET_RESTAURANT_REVIEWS_REQUEST: "GET_RESTAURANT_REVIEWS_REQUEST",
    GET_RESTAURANT_REVIEWS_SUCCESS: "GET_RESTAURANT_REVIEWS_SUCCESS",
    GET_RESTAURANT_REVIEWS_FAILURE: "GET_RESTAURANT_REVIEWS_FAILURE",
}

export const initialState = {
  reviews: null,
  error: ''
};

export default (state = initialState, action) => {
    switch (action.type) {
        case types.GET_RESTAURANT_REVIEWS_REQUEST: {
            return {
                ...state
            };
        }

        case types.GET_RESTAURANT_REVIEWS_SUCCESS: {
            return {
                ...state,
                reviews: action.data,
                error: ''
            };
        }

        case types.GET_RESTAURANT_REVIEWS_FAILURE: {
            return {
                ...state,
                error: action.error,
            }
        }

        default:
            return state;
  }
}

export const reviewsActions = {
    startGetAllReviewsForRestaurantRequest: restaurantId => ({ type: types.GET_RESTAURANT_REVIEWS_REQUEST, restaurantId }),
    successfulGetAllReviewsForRestaurantRequest: data => ({ type: types.GET_RESTAURANT_REVIEWS_SUCCESS, data }),
    failedGetAllReviewsForRestaurantRequest: error => ({ type: types.GET_RESTAURANT_REVIEWS_FAILURE, error }),
}
