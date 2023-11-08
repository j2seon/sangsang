import * as React from 'react';

import {IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import dayjs from "dayjs";
import {Delete} from "@mui/icons-material";

const BasicTable = ({list, tableHeadData, color}) => {

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
                <TableRow
                    key={row.memberId}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {row.memberId}
                  </TableCell>
                  <TableCell>{row.memberName}</TableCell>
                  <TableCell>{row.auth}</TableCell>
                  <TableCell>{row.zipCode}</TableCell>
                  <TableCell>{row.address}</TableCell>
                  <TableCell>{row.createdAt}</TableCell>
                  <TableCell>
                    <IconButton aria-label="delete">
                      <Delete />
                    </IconButton>
                  </TableCell>
                </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
  );
}

export default BasicTable;