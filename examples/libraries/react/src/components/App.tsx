import React from "react";
import {Route, Switch} from "react-router";
import {BrowserRouter, Link} from "react-router-dom";
import {ClassComponent, FunctionalComponentAsConstant, FunctionalComponentAsFunction} from "./VariousComponentTypes";

export const App = () => {
    return (
        <div>
            <header>
                <h1>Hello World!</h1>
            </header>
            <BrowserRouter>
                <nav>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/components">Components</Link></li>
                    </ul>
                </nav>
                <Switch>
                    <Route path="/components">
                        <h2>Functional Component (declared as constant)</h2>
                        <p><FunctionalComponentAsConstant name="John" city="Seattle"/></p>
                        <h2>Functional Component (declared as function)</h2>
                        <p><FunctionalComponentAsFunction name="Ahmed" city="Alexandria"/></p>
                        <h2>Class Component</h2>
                        <p><ClassComponent name="Milan" city="Bratislava"/></p>
                    </Route>
                </Switch>
            </BrowserRouter>
        </div>
    );
};
