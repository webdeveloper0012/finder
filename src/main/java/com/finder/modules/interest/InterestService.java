package com.finder.modules.interest;

import com.finder.exceptions.ErrorHolder;
import com.finder.modules.category.Category;
import com.finder.modules.category.CategoryService;
import com.finder.modules.generics.GenericEntity;
import com.finder.modules.generics.GenericService;
import com.finder.scope.request.FinderParamConstant;
import com.finder.scope.request.FinderParams;
import com.finder.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService extends GenericService<Interest> {
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    CategoryService categoryService;

    @Autowired
    FinderParams finderParams;

    public InterestService(InterestRepository genericRepository) {
        super(genericRepository, "Interest");
    }

    /**
     * comma separated interests to be updated for a category given
     *
     * @param interests
     */
    public void postMultipleInterests(String interests, String category) {
        String[] interestsArray = interests.split(",");
        for (String interest : interestsArray) {
            Interest interestEntity = new Interest();
            interestEntity.setInterest(interest);
            interestEntity.setCategory(category);
            this.save(interestEntity);
        }
    }

    @Override
    public <T extends GenericEntity> void postValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        Interest interest = (Interest) newEntity;
        if (isNewObject(interest)) {
            if (interest.getNumberOfUsers() == null) {
                interest.setNumberOfUsers(0L);
            }
            //maintain lowercase to help in retrieval and search
            interest.setInterest(interest.getInterest().toLowerCase());
        }
        super.postValidate(newEntity, errorHolder);
    }

    @Override
    public <T extends GenericEntity> void preValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        Interest interest = (Interest) newEntity;
        String categoryId = finderParams.getString(FinderParamConstant.IS_CATEGORY_VALIDATED.getValue());
        if (categoryId == null) {
            Category category = categoryService.findByCategory(interest.getCategory());
            if (category == null) {
                errorHolder.addError("Category", "Category not found!");
            } else {
                interest.setCategoryId(category.getId());
                finderParams.putRequestParam(FinderParamConstant.IS_CATEGORY_VALIDATED.getValue(), category.getId());
            }
        } else {
            interest.setCategoryId(categoryId);
        }
        if (interestRepository.findByInterest(interest.getInterest()) != null) {
            errorHolder.addError("Interest", interest.getInterest() + " already exists!");
        }
        super.preValidate(newEntity, errorHolder);
    }

    public List<Interest> getInterests(String interest, Integer offset) {
        if (offset == null) {
            offset = 1;
        }
        if (interest != null) {
            interest = interest.toLowerCase();
        }
        PageRequest pageRequest = PageRequest.of(offset, Constants.PAGINATION_SIZE);
        return interestRepository.findByInterestLike(interest, pageRequest);
    }

    public Interest findByInterest(String interest) {
        return interestRepository.findByInterest(interest);
    }
}
