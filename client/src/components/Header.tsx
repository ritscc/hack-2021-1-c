import { HeaderTab } from "./HeaderTab";

import User from "./User";
import Logo from "./Logo";

export const Header = () => (
  <>
    <div
      style={{
        position: "fixed",
        backgroundColor: "#333333",
        width: "100%",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
      }}
    >
      <Logo></Logo>
      <HeaderTab title="Profile" link="./" />
      <HeaderTab title="Stamp" link="./" />
      <User></User>
    </div>
  </>
);
