import React from "react";
import AppBar from "@material-ui/core/AppBar";

import CssBaseline from "@material-ui/core/CssBaseline";
import Toolbar from "@material-ui/core/Toolbar";
import { ThemeProvider } from "@material-ui/styles";
import Link from "@material-ui/core/Link";

import Logo from "./Logo";
import User from "./User";
import Button from "@material-ui/core/Button";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import Grid from "@material-ui/core/Grid";
import StarIcon from "@material-ui/icons/StarBorder";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";

import { useStyles, theme } from "./themeHeader";

export default function Header() {
  const classes = useStyles();

  return (
    <React.Fragment>
      <ThemeProvider theme={theme}>
        <AppBar
          position="static"
          color="secondary"
          elevation={0}
          className={classes.appBar}
        >
          <Toolbar className={classes.toolbar}>
            <Logo></Logo>

            <nav>
              <Link
                variant="button"
                color="inherit"
                href="#"
                className={classes.link}
              >
                Profile
              </Link>
              <Link
                variant="button"
                color="inherit"
                href="#"
                className={classes.link}
              >
                Stamp
              </Link>
            </nav>

            <User></User>
          </Toolbar>
        </AppBar>
      </ThemeProvider>
    </React.Fragment>
  );
}
