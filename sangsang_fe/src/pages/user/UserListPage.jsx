import React, {useState} from 'react';
import {Link} from "react-router-dom";

function UserListPage() {
    const [data, setData] = useState({
        totalPage : 1,
        list: [],
    });


    return (
        <div>
            <Link to={"add"}>유저 추가하기</Link>
        </div>
    );
}

export default UserListPage;