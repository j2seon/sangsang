import React from 'react';
import {Navigate, Outlet} from "react-router-dom";
import Header from "../components/nav/Header";
import PrivateRoute from "./PrivateRoute";


export default function Layouts() {

    return (
        <div className={'wrapper'}>
            <Header/>
            <Outlet/>
        </div>
    );
}
