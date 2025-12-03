package com.example.smartAir;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.smartAir.domain.UserRole;
import com.example.smartAir.userinfo.UserBasicInfo;



public class LoginPresenterTest {

    @Mock
    private LoginPresenter.View view;

    @Mock
    private LoginModel model;

    private LoginPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        presenter = new LoginPresenter(view, model);
    }

    // login

    @Test
    public void loginWithEmail_emptyEmail_showsError() {
        presenter.loginWithEmail("", "password");
        verify(view).showError("Please enter the email and password.");
        verifyNoInteractions(model);
    }

    @Test
    public void loginWithEmail_emptyPassword_showsError() {
        presenter.loginWithEmail("user", "");
        verify(view).showError("Please enter the email and password.");
        verifyNoInteractions(model);
    }

    @Test
    public void loginWithEmail_missingAtSymbol_appendsDomain() {
        presenter.loginWithEmail("person", "pw");

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);

        verify(model).authenticateEmailUser(emailCaptor.capture(), eq("pw"), any());
        assert(emailCaptor.getValue().equals("person@smartair.com"));
    }

    @Test
    public void loginWithEmail_validInputs_callsModel_andShowsLoading() {
        presenter.loginWithEmail("person@test.com", "123blah");

        verify(view).showLoading(true);
        verify(model).authenticateEmailUser(eq("person@test.com"), eq("123blah"), any());
    }

    @Test
    public void loginWithEmail_modelSuccess_hidesLoading_showsMessage_navigates() {
        // Capture callback
        ArgumentCaptor<LoginModel.LoginCallback> captor = ArgumentCaptor.forClass(LoginModel.LoginCallback.class);

        presenter.loginWithEmail("user@test.com", "pass4567");

        verify(model).authenticateEmailUser(eq("user@test.com"), eq("pass4567"), captor.capture());


        LoginModel.LoginCallback cb = captor.getValue();
        cb.onSuccess("uid123", UserRole.PARENT);

        verify(view).showLoading(false);
        verify(view).showMessage("Welcome!");


        verify(view).navigateToDashboard();
    }

    @Test
    public void loginWithEmail_modelError_hidesLoading_showsError() {
        ArgumentCaptor<LoginModel.LoginCallback> captor = ArgumentCaptor.forClass(LoginModel.LoginCallback.class);

        presenter.loginWithEmail("user@test.com", "pw12");

        verify(model).authenticateEmailUser(eq("user@test.com"), eq("pw12"), captor.capture());

        LoginModel.LoginCallback cb = captor.getValue();
        cb.onError("Invalid");

        verify(view).showLoading(false);
        verify(view).showError("Invalid");
    }


    // recovery

    @Test
    public void recoverPassword_emptyEmail_showsError() {
        presenter.recoverPassword("");

        verify(view).showError("Enter your email.");
        verifyNoInteractions(model);
    }

    @Test
    public void recoverPassword_validEmail_showsLoading_callsModel() {
        presenter.recoverPassword("user@test.com");

        verify(view).showLoading(true);
        verify(model).sendRecoveryEmail(eq("user@test.com"), any());
    }

    @Test
    public void recoverPassword_modelSuccess_hidesLoading_showsMessage() {
        ArgumentCaptor<LoginModel.RecoveryCallback> captor = ArgumentCaptor.forClass(LoginModel.RecoveryCallback.class);

        presenter.recoverPassword("user@test.com");

        verify(model).sendRecoveryEmail(eq("user@test.com"), captor.capture());

        LoginModel.RecoveryCallback cb = captor.getValue();
        cb.onSuccess("Recovery email has been sent!");

        verify(view).showLoading(false);
        verify(view).showMessage("Recovery email has been sent!");
    }

    @Test
    public void recoverPassword_modelError_hidesLoading_showsError() {
        ArgumentCaptor<LoginModel.RecoveryCallback> captor = ArgumentCaptor.forClass(LoginModel.RecoveryCallback.class);

        presenter.recoverPassword("user@test.com");

        verify(model).sendRecoveryEmail(eq("user@test.com"), captor.capture());

        LoginModel.RecoveryCallback cb = captor.getValue();
        cb.onError("Invalid.");

        verify(view).showLoading(false);
        verify(view).showError("Invalid.");
    }
}