package guru.springframework.repositories;

import guru.springframework.configuration.RepositoryConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by farou_000 on 29/10/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class GroupRepositoryTest {

    private GroupRepository groupRepository;

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Test
    public void testSaveGroup() {
        //setup product

     /*   User user1 = new User();
        user1.setFirstName("Farouk");
        user1.setLastName("Ennia");
        user1.setBiography("sdfsdfsdq");
        user1.setEmailAdresse("sdfsdfsdfsd");

        Set<User> users = new HashSet<>();
        users.add(user1);


        Group group = new Group();
        group.setName("Man u Lovers");
        group.setDiscutionBoard("WE LOOOOOOOOOOOOVE IT");
        group.setDescription("This group is for man u fans");
        group.setAdminEmail("Farouk Ennia");


        //save product, verify his name value after save
        assertNull(group.getGroupMembers()); //null before save
        groupRepository.save(group);
        assertNotNull(group.getName()); //not null after save


        //adding user to group
        group.setGroupMembers(users);
        groupRepository.save(group);
        assertNotNull(group.getGroupMembers());// not null after save


        //fetch from DB
        Group fetchedGroup = groupRepository.findOne(group.getName());

        //should not be null
        assertNotNull(fetchedGroup);

        //should equal
        assertEquals(group.getName(), fetchedGroup.getName());
        assertEquals(group.getAdminEmail(), fetchedGroup.getAdminEmail());

        //update description and save
        fetchedGroup.setDescription("New Description");
        groupRepository.save(fetchedGroup);

        //get from DB, should be updated
        Group fetchedUpdatedGroup = groupRepository.findOne(fetchedGroup.getName());
        assertEquals(fetchedGroup.getDescription(), fetchedUpdatedGroup.getDescription());

        //verify count of products in DB
        long groupCount = groupRepository.count();
        assertEquals(groupCount, 1);

        //get all products, list should only have one
        Iterable<Group> groups = groupRepository.findAll();

        int count = 0;

        for (Group g : groups) {
            count++;
        }

        assertEquals(count, 1);
        */
    }

}
