import { createTheme, makeStyles } from "@material-ui/core/styles";

export const theme = createTheme({
  palette: {
    primary: {
      main: "#A3161C",
    },
    secondary: {
      main: "#333333",
    },
  },
});

export const useStyles = makeStyles((theme) => ({
  "@global": {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: "none",
    },
  },
  appBar: {
    borderBottom: `1px solid ${theme.palette.divider}`,
  },
  toolbar: {
    flexWrap: "wrap",
  },
  toolbarTitle: {
    flexGrow: 1,
  },
  link: {
    margin: theme.spacing(1, 1.5),
  },
}));
