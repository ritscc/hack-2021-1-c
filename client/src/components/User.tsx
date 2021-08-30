import Button from "@material-ui/core/Button";
import { useStyles } from "./themeHeader";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";

export default function User() {
  const classes = useStyles();
  return (
    <>
      <AccountCircleIcon
        color="primary"
        fontSize="large"
        className={classes.link}
      />

      <Button
        href="#"
        color="primary"
        variant="outlined"
        className={classes.link}
      >
        User
      </Button>
    </>
  );
}
