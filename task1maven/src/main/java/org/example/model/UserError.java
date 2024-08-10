package org.example.model;

public class UserError {
    private String errorMessage;
    private int errorCode;
    private boolean success;
    private UserModel userModel;
    private DepartmentModel departmentModel;

    public UserError(String errorMessage, int errorCode, boolean success) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.success = success;
    }

    public UserError(String errorMessage, int errorCode, boolean success, UserModel userModel, DepartmentModel departmentModel) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.success = success;
        this.userModel = userModel;
        this.departmentModel = departmentModel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public DepartmentModel getDepartmentModel() {
        return departmentModel;
    }

    public void setDepartmentModel(DepartmentModel departmentModel) {
        this.departmentModel = departmentModel;
    }
}
