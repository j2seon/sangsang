import * as React from 'react';

import {IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import dayjs from "dayjs";
import {Delete} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";
import {LoadingSpinner} from "../other/LoadingSpinner";
import {authKor, changeAuth} from "../../../util/validationUtil";

const BasicTable = ({list, tableHeadData, color, onClick, onWithdrawal}) => {
  const navigate = useNavigate();

  const handelWithdrawal = (memberId) => {
    onWithdrawal.mutate(memberId);
  }



  if(!list) return <LoadingSpinner/>;

  return (
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow sx={color}>
              {
                tableHeadData.map((rows, index) => (
                  <TableCell key={index}>{rows}</TableCell>
                ))
              }
            </TableRow>
          </TableHead>
          <TableBody>
            {list.map((row) => (
                <TableRow key={row.memberId} sx={{ '&:last-child td, &:last-child th': { border: 0 }, }}>
                  <TableCell component="th"
                             scope="row"
                             sx={{cursor:'pointer'}}
                             onClick={()=> onClick(row.memberId)}>
                    {row.memberId}
                  </TableCell>
                  <TableCell>{row.memberName}</TableCell>
                  <TableCell>{authKor(row.auth)}</TableCell>
                  <TableCell>{row.zipCode}</TableCell>
                  <TableCell>{row.address}</TableCell>
                  <TableCell>{row.createdAt}</TableCell>
                  <TableCell>
                    {
                      row.deletedAt ?
                        row.deletedAt
                        :
                        <IconButton aria-label="delete"
                                    onClick={()=>{
                                      handelWithdrawal(row.memberId)
                                    }}>
                          <Delete />
                        </IconButton>
                    }
                  </TableCell>
                </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
  );
}

export default BasicTable;