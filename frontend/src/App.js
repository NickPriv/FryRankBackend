import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { Provider } from "react-redux";
import store from "./redux/store";
import Homepage from './components/Homepage';
import Restaurants from './containers/Restaurants';
import Reviews from './containers/Reviews';

function App() {
  return (
    <div>
      <Provider store={store}>
        <Router>
          <Switch>
            <Route exact path="/" component={Homepage} />
            <Route path="/reviews/:restaurantId" component={Reviews} />
            // TODO: The path for this endpoint should just be /restaurants, but since we share ports with the
            // backend service which also has the endpoint /restaurants, we need to come up with a different name for
            // now. Eventually, we should migrate this to /restaurants.
            <Route path="/restaurantspage" component={Restaurants} />
          </Switch>
        </Router>
      </Provider>
    </div>
  );
}

export default App;
