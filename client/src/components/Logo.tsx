import Typography from "@material-ui/core/Typography";
import { useStyles } from "./themeHeader";
import SettingsIcon from "@material-ui/icons/Settings";
export default function Logo() {
  const classes = useStyles();
  return (
    <Typography
      variant="h6"
      color="inherit"
      noWrap
      className={classes.toolbarTitle}
    >
      <SettingsIcon />
    </Typography>
  );
}
