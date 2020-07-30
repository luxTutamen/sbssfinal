package ua.axiom.testing.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ua.axiom.model.Admin;
import ua.axiom.model.User;
import ua.axiom.repository.AdminRepository;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.appservice.AdminService;
import ua.axiom.service.appservice.ClientService;
import ua.axiom.service.appservice.DriverService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;
import static ua.axiom.testing.TestModelEntitiesCreator.getAdminList;
import static ua.axiom.testing.TestModelEntitiesCreator.getClient;

@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private DriverService driverService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;// = new AdminService(getClientRepositoryMock(), getDriverRepositoryMock(), adminRepository, userRepository);


    @Test
    public void testGetAdmins() {
        when(adminRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(getAdminList()));

        List<Admin> adminsList = adminService.getAdmins(1, 1);

        assertArrayEquals(adminsList.toArray(), getAdminList().toArray());
    }

    @Test
    public void testPromoteUser() {
        User user = getClient();

        when(userRepository.findById(longThat(along -> along.equals(user.getId())))).thenReturn(Optional.of(user));

        adminService.promoteUser(user.getId());

        verify(userRepository, times(1)).delete(argThat(auser -> auser.getId().equals(user.getId())));
        verify(adminRepository, times(1)).save(argThat(auser -> auser.getId().equals(user.getId())));
    }

    @Test
    public void testBanUser() {
        User user = getClient();

        when(userRepository.findById(longThat(arg -> arg.equals(user.getId())))).thenReturn(Optional.of(user));

        adminService.banUser(user.getId());

        verify(userRepository, times(1)).save(argThat(arg -> arg.getId().equals(user.getId())));

    }






}
