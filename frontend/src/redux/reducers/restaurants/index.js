export const types = {
    GET_RESTAURANTS_REQUEST: "GET_RESTAURANTS_REQUEST",
    GET_RESTAURANTS_SUCCESS: "GET_RESTAURANTS_SUCCESS",
    GET_RESTAURANTS_FAILURE: "GET_RESTAURANTS_FAILURE",
}

export const initialState = {
  restaurants: [],
  error: ''
};

export default (state = initialState, action) => {
    switch (action.type) {
        case types.GET_RESTAURANTS_REQUEST: {
            return {
                ...state
            };
        }

        case types.GET_RESTAURANTS_SUCCESS: {
            return {
                ...state,
                restaurants: action.data,
                error: ''
            };
        }

        case types.GET_RESTAURANTS_FAILURE: {
            return {
                ...state,
                error: action.error,
            }
        }

        default:
            return state;
  }
}

export const restaurantsActions = {
    startGetRestaurantsRequest: () => ({ type: types.GET_RESTAURANTS_REQUEST }),
    successfulGetRestaurantsRequest: data => ({ type: types.GET_RESTAURANTS_SUCCESS, data }),
    failedGetRestaurantsRequest: error => ({ type: types.GET_RESTAURANTS_FAILURE, error }),
}